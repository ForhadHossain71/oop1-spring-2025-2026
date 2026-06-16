document.addEventListener("DOMContentLoaded", () => {
  const app = new LiveTVApp();
  app.init();
});

class LiveTVApp {
  constructor() {
    this.player = null;
    this.hls = null;
    this.currentChannel = null;
    this.activeCategory = "all";
    this.searchQuery = "";
    this.favorites = JSON.parse(localStorage.getItem("tv_favorites") || "[]");
    this.showFavoritesOnly = false;
    this.volume = parseFloat(localStorage.getItem("tv_volume") || "0.8");
    this.isMuted = false;
    this.miniPlayerMode = false;
    this.sidebarOpen = false;
  }

  init() {
    this.cacheDOM();
    this.renderCategories();
    this.renderChannels();
    this.bindEvents();
    this.setupKeyboardShortcuts();
    this.updateTime();
    setInterval(() => this.updateTime(), 1000);

    // Auto-play first channel
    if (CHANNELS.length > 0) {
      this.playChannel(CHANNELS[0]);
    }
  }

  cacheDOM() {
    this.channelGrid = document.getElementById("channelGrid");
    this.categoryList = document.getElementById("categoryList");
    this.searchInput = document.getElementById("searchInput");
    this.videoPlayer = document.getElementById("videoPlayer");
    this.playerSection = document.getElementById("playerSection");
    this.channelName = document.getElementById("channelName");
    this.channelDesc = document.getElementById("channelDesc");
    this.channelCountry = document.getElementById("channelCountry");
    this.channelCategory = document.getElementById("channelCategory");
    this.liveIndicator = document.getElementById("liveIndicator");
    this.volumeSlider = document.getElementById("volumeSlider");
    this.volumeIcon = document.getElementById("volumeIcon");
    this.favBtn = document.getElementById("favBtn");
    this.fullscreenBtn = document.getElementById("fullscreenBtn");
    this.miniPlayerBtn = document.getElementById("miniPlayerBtn");
    this.favFilterBtn = document.getElementById("favFilterBtn");
    this.channelCount = document.getElementById("channelCount");
    this.currentTime = document.getElementById("currentTime");
    this.sidebar = document.getElementById("sidebar");
    this.sidebarToggle = document.getElementById("sidebarToggle");
    this.sidebarOverlay = document.getElementById("sidebarOverlay");
    this.playerOverlay = document.getElementById("playerOverlay");
    this.noResults = document.getElementById("noResults");
    this.loadingSpinner = document.getElementById("loadingSpinner");
  }

  bindEvents() {
    this.searchInput.addEventListener("input", (e) => {
      this.searchQuery = e.target.value.toLowerCase();
      this.renderChannels();
    });

    this.volumeSlider.addEventListener("input", (e) => {
      this.volume = parseFloat(e.target.value);
      this.videoPlayer.volume = this.volume;
      this.isMuted = this.volume === 0;
      this.updateVolumeIcon();
      localStorage.setItem("tv_volume", this.volume);
    });

    this.volumeIcon.addEventListener("click", () => this.toggleMute());
    this.favBtn.addEventListener("click", () => this.toggleFavorite());
    this.fullscreenBtn.addEventListener("click", () => this.toggleFullscreen());
    this.miniPlayerBtn.addEventListener("click", () => this.toggleMiniPlayer());

    this.favFilterBtn.addEventListener("click", () => {
      this.showFavoritesOnly = !this.showFavoritesOnly;
      this.favFilterBtn.classList.toggle("active", this.showFavoritesOnly);
      this.renderChannels();
    });

    this.sidebarToggle.addEventListener("click", () => this.toggleSidebar());
    this.sidebarOverlay.addEventListener("click", () => this.closeSidebar());

    this.videoPlayer.addEventListener("playing", () => {
      this.liveIndicator.classList.add("active");
      this.hideLoading();
      this.hidePlayerOverlay();
    });

    this.videoPlayer.addEventListener("waiting", () => {
      this.showLoading();
    });

    this.videoPlayer.addEventListener("error", () => {
      this.showPlayerOverlay("Stream unavailable", "This channel may be temporarily offline. Try another channel.");
    });

    this.playerOverlay.querySelector(".retry-btn")?.addEventListener("click", () => {
      if (this.currentChannel) this.playChannel(this.currentChannel);
    });
  }

  setupKeyboardShortcuts() {
    document.addEventListener("keydown", (e) => {
      if (e.target.tagName === "INPUT") return;
      switch (e.key) {
        case " ":
          e.preventDefault();
          this.videoPlayer.paused ? this.videoPlayer.play() : this.videoPlayer.pause();
          break;
        case "m":
          this.toggleMute();
          break;
        case "f":
          this.toggleFullscreen();
          break;
        case "ArrowUp":
          e.preventDefault();
          this.volume = Math.min(1, this.volume + 0.1);
          this.applyVolume();
          break;
        case "ArrowDown":
          e.preventDefault();
          this.volume = Math.max(0, this.volume - 0.1);
          this.applyVolume();
          break;
        case "/":
          e.preventDefault();
          this.searchInput.focus();
          break;
        case "Escape":
          this.searchInput.blur();
          this.closeSidebar();
          break;
      }
    });
  }

  renderCategories() {
    this.categoryList.innerHTML = CATEGORIES.map(
      (cat) => `
      <button class="category-btn ${cat.id === this.activeCategory ? "active" : ""}"
              data-category="${cat.id}"
              onclick="document.querySelector('.live-tv-app').__app.setCategory('${cat.id}')">
        <i class="${cat.icon}"></i>
        <span>${cat.name}</span>
      </button>
    `
    ).join("");
  }

  setCategory(categoryId) {
    this.activeCategory = categoryId;
    document.querySelectorAll(".category-btn").forEach((btn) => {
      btn.classList.toggle("active", btn.dataset.category === categoryId);
    });
    this.renderChannels();
    this.closeSidebar();
  }

  getFilteredChannels() {
    return CHANNELS.filter((ch) => {
      const matchesCategory = this.activeCategory === "all" || ch.category === this.activeCategory;
      const matchesSearch =
        !this.searchQuery ||
        ch.name.toLowerCase().includes(this.searchQuery) ||
        ch.category.toLowerCase().includes(this.searchQuery) ||
        ch.country.toLowerCase().includes(this.searchQuery);
      const matchesFav = !this.showFavoritesOnly || this.favorites.includes(ch.id);
      return matchesCategory && matchesSearch && matchesFav;
    });
  }

  renderChannels() {
    const filtered = this.getFilteredChannels();
    this.channelCount.textContent = `${filtered.length} channel${filtered.length !== 1 ? "s" : ""}`;

    if (filtered.length === 0) {
      this.channelGrid.innerHTML = "";
      this.noResults.style.display = "flex";
      return;
    }

    this.noResults.style.display = "none";
    this.channelGrid.innerHTML = filtered
      .map(
        (ch) => `
      <div class="channel-card ${this.currentChannel?.id === ch.id ? "active" : ""}"
           data-id="${ch.id}"
           onclick="document.querySelector('.live-tv-app').__app.playChannel(CHANNELS.find(c=>c.id==='${ch.id}'))">
        <div class="channel-card-header">
          <div class="channel-logo-wrap">
            <img src="${ch.logo}" alt="${ch.name}" class="channel-logo"
                 onerror="this.src='data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><rect fill=%22%23333%22 width=%22100%22 height=%22100%22/><text x=%2250%22 y=%2255%22 text-anchor=%22middle%22 fill=%22%23888%22 font-size=%2230%22>TV</text></svg>'">
          </div>
          ${this.currentChannel?.id === ch.id ? '<span class="live-badge"><i class="fas fa-circle"></i> LIVE</span>' : ""}
          <button class="fav-icon ${this.favorites.includes(ch.id) ? "active" : ""}"
                  onclick="event.stopPropagation(); document.querySelector('.live-tv-app').__app.toggleFavoriteById('${ch.id}')">
            <i class="${this.favorites.includes(ch.id) ? "fas" : "far"} fa-heart"></i>
          </button>
        </div>
        <div class="channel-card-body">
          <h3 class="channel-card-name">${ch.name}</h3>
          <div class="channel-card-meta">
            <span class="channel-tag category-tag">${ch.category}</span>
            <span class="channel-tag country-tag">${ch.country}</span>
          </div>
        </div>
      </div>
    `
      )
      .join("");
  }

  playChannel(channel) {
    if (!channel) return;
    this.currentChannel = channel;

    // Update player info
    this.channelName.textContent = channel.name;
    this.channelDesc.textContent = channel.description;
    this.channelCountry.textContent = channel.country;
    this.channelCategory.textContent = channel.category;
    this.liveIndicator.classList.remove("active");

    // Update favorite button
    this.favBtn.innerHTML = this.favorites.includes(channel.id)
      ? '<i class="fas fa-heart"></i>'
      : '<i class="far fa-heart"></i>';
    this.favBtn.classList.toggle("active", this.favorites.includes(channel.id));

    // Show loading
    this.showLoading();
    this.hidePlayerOverlay();

    // Destroy previous HLS instance
    if (this.hls) {
      this.hls.destroy();
      this.hls = null;
    }

    // Play stream
    if (Hls.isSupported()) {
      this.hls = new Hls({
        enableWorker: true,
        lowLatencyMode: true,
        maxBufferLength: 30,
        maxMaxBufferLength: 60,
        startLevel: -1,
        fragLoadingTimeOut: 20000,
        manifestLoadingTimeOut: 20000,
        levelLoadingTimeOut: 20000,
      });
      this.hls.loadSource(channel.stream);
      this.hls.attachMedia(this.videoPlayer);
      this.hls.on(Hls.Events.MANIFEST_PARSED, () => {
        this.videoPlayer.play().catch(() => {});
      });
      this.hls.on(Hls.Events.ERROR, (_, data) => {
        if (data.fatal) {
          switch (data.type) {
            case Hls.ErrorTypes.NETWORK_ERROR:
              this.hls.startLoad();
              break;
            case Hls.ErrorTypes.MEDIA_ERROR:
              this.hls.recoverMediaError();
              break;
            default:
              this.showPlayerOverlay(
                "Stream Error",
                "This channel is currently unavailable. Please try another channel."
              );
              break;
          }
        }
      });
    } else if (this.videoPlayer.canPlayType("application/vnd.apple.mpegurl")) {
      this.videoPlayer.src = channel.stream;
      this.videoPlayer.play().catch(() => {});
    } else {
      this.showPlayerOverlay("Unsupported", "Your browser does not support HLS streaming.");
    }

    this.videoPlayer.volume = this.isMuted ? 0 : this.volume;
    this.renderChannels();
    this.scrollToPlayer();
  }

  scrollToPlayer() {
    if (window.innerWidth <= 768) {
      this.playerSection.scrollIntoView({ behavior: "smooth", block: "start" });
    }
  }

  toggleFavorite() {
    if (!this.currentChannel) return;
    this.toggleFavoriteById(this.currentChannel.id);
  }

  toggleFavoriteById(channelId) {
    const idx = this.favorites.indexOf(channelId);
    if (idx > -1) {
      this.favorites.splice(idx, 1);
    } else {
      this.favorites.push(channelId);
    }
    localStorage.setItem("tv_favorites", JSON.stringify(this.favorites));

    if (this.currentChannel?.id === channelId) {
      this.favBtn.innerHTML = this.favorites.includes(channelId)
        ? '<i class="fas fa-heart"></i>'
        : '<i class="far fa-heart"></i>';
      this.favBtn.classList.toggle("active", this.favorites.includes(channelId));
    }
    this.renderChannels();
  }

  toggleMute() {
    this.isMuted = !this.isMuted;
    this.videoPlayer.volume = this.isMuted ? 0 : this.volume;
    this.volumeSlider.value = this.isMuted ? 0 : this.volume;
    this.updateVolumeIcon();
  }

  updateVolumeIcon() {
    const vol = this.isMuted ? 0 : this.volume;
    if (vol === 0) this.volumeIcon.className = "fas fa-volume-mute";
    else if (vol < 0.5) this.volumeIcon.className = "fas fa-volume-down";
    else this.volumeIcon.className = "fas fa-volume-up";
  }

  applyVolume() {
    this.videoPlayer.volume = this.volume;
    this.volumeSlider.value = this.volume;
    this.isMuted = this.volume === 0;
    this.updateVolumeIcon();
    localStorage.setItem("tv_volume", this.volume);
  }

  toggleFullscreen() {
    const container = this.playerSection.querySelector(".player-wrapper");
    if (!document.fullscreenElement) {
      (container.requestFullscreen || container.webkitRequestFullscreen || container.msRequestFullscreen).call(
        container
      );
    } else {
      (document.exitFullscreen || document.webkitExitFullscreen || document.msExitFullscreen).call(document);
    }
  }

  toggleMiniPlayer() {
    this.miniPlayerMode = !this.miniPlayerMode;
    this.playerSection.classList.toggle("mini-player", this.miniPlayerMode);
    this.miniPlayerBtn.innerHTML = this.miniPlayerMode
      ? '<i class="fas fa-expand"></i>'
      : '<i class="fas fa-compress"></i>';
  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
    this.sidebar.classList.toggle("open", this.sidebarOpen);
    this.sidebarOverlay.classList.toggle("active", this.sidebarOpen);
  }

  closeSidebar() {
    this.sidebarOpen = false;
    this.sidebar.classList.remove("open");
    this.sidebarOverlay.classList.remove("active");
  }

  showLoading() {
    this.loadingSpinner.style.display = "flex";
  }

  hideLoading() {
    this.loadingSpinner.style.display = "none";
  }

  showPlayerOverlay(title, message) {
    this.hideLoading();
    const overlay = this.playerOverlay;
    overlay.querySelector("h3").textContent = title;
    overlay.querySelector("p").textContent = message;
    overlay.style.display = "flex";
  }

  hidePlayerOverlay() {
    this.playerOverlay.style.display = "none";
  }

  updateTime() {
    const now = new Date();
    this.currentTime.textContent = now.toLocaleTimeString("en-US", {
      hour: "2-digit",
      minute: "2-digit",
      hour12: true,
    });
  }
}
