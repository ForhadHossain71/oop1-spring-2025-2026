const CHANNELS = [
  // ── News ──
  {
    id: "aljaz-eng",
    name: "Al Jazeera English",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/f/f2/Aljazeera_eng.svg/1200px-Aljazeera_eng.svg.png",
    category: "News",
    country: "Qatar",
    stream: "https://live-hls-web-aje.getaj.net/AJE/01.m3u8",
    description: "24/7 international news from Al Jazeera's English channel."
  },
  {
    id: "france24-eng",
    name: "France 24 English",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/France_24_logo_%282018%29.svg/1200px-France_24_logo_%282018%29.svg.png",
    category: "News",
    country: "France",
    stream: "https://stream.france24.com/live/hls/fren-hls/index.m3u8",
    description: "International news and current affairs from France."
  },
  {
    id: "dw-eng",
    name: "DW English",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/Deutsche_Welle_symbol_2012.svg/1200px-Deutsche_Welle_symbol_2012.svg.png",
    category: "News",
    country: "Germany",
    stream: "https://dwamdstream104.akamaized.net/hls/live/2015530/dwstream104/index.m3u8",
    description: "Germany's international broadcaster – news, analysis and culture."
  },
  {
    id: "euronews",
    name: "Euronews",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Euronews_2022.svg/1200px-Euronews_2022.svg.png",
    category: "News",
    country: "France",
    stream: "https://rakuten-euronews-2-pt.samsung.wurl.tv/manifest/playlist.m3u8",
    description: "European and world news from a pan-European perspective."
  },
  {
    id: "nhk-world",
    name: "NHK World Japan",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/NHK_World-Japan_logo.svg/1200px-NHK_World-Japan_logo.svg.png",
    category: "News",
    country: "Japan",
    stream: "https://nhkworld.webcdn.stream.ne.jp/www11/nhkworld-tv/domestic/263942/live_wa_s.m3u8",
    description: "Japan's public broadcaster for international audiences."
  },
  {
    id: "cgtn",
    name: "CGTN",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/CGTN.svg/1200px-CGTN.svg.png",
    category: "News",
    country: "China",
    stream: "https://news.cgtn.com/resource/live/english/cgtn-news.m3u8",
    description: "China Global Television Network – global news coverage."
  },
  {
    id: "rt",
    name: "RT News",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Russia-today-logo.svg/1200px-Russia-today-logo.svg.png",
    category: "News",
    country: "Russia",
    stream: "https://rt-glb.rttv.com/live/rtnews/playlist.m3u8",
    description: "International news channel based in Russia."
  },
  {
    id: "trt-world",
    name: "TRT World",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/TRT_World.svg/1200px-TRT_World.svg.png",
    category: "News",
    country: "Turkey",
    stream: "https://tv-trtworld.medya.trt.com.tr/master.m3u8",
    description: "Turkey's English-language public broadcaster."
  },
  {
    id: "arirang",
    name: "Arirang TV",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Arirang_TV.svg/1200px-Arirang_TV.svg.png",
    category: "News",
    country: "South Korea",
    stream: "https://arigaem.pc.cdn.bitgravity.com/arirang_1ch/smil:arirang_1ch.smil/playlist.m3u8",
    description: "South Korea's international English channel."
  },
  {
    id: "abc-australia",
    name: "ABC News Australia",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/ABC_News_%28Australia%29_logo_2024.svg/1200px-ABC_News_%28Australia%29_logo_2024.svg.png",
    category: "News",
    country: "Australia",
    stream: "https://abc-iview-mediapackagestreams-2.akamaized.net/out/v1/6e1cc6d25ec0480ea099a5399d73bc4b/index.m3u8",
    description: "Australia's national broadcaster – 24-hour news channel."
  },

  // ── Sports ──
  {
    id: "nba-tv",
    name: "NBA TV",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/d/d2/NBA_TV.svg/1200px-NBA_TV.svg.png",
    category: "Sports",
    country: "USA",
    stream: "https://dai2.xumo.com/amagi_hls_data_xumo1212A-xumo-nbatv/CDN/master.m3u8",
    description: "Dedicated NBA basketball coverage and analysis."
  },
  {
    id: "redbull-tv",
    name: "Red Bull TV",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/e/e6/Red_Bull_TV_logo.svg/1200px-Red_Bull_TV_logo.svg.png",
    category: "Sports",
    country: "Austria",
    stream: "https://rbmn-live.akamaized.net/hls/live/590964/BosssChannelHLS/master.m3u8",
    description: "Extreme sports, music, and adventure content."
  },
  {
    id: "fight-network",
    name: "Fight Network",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/f/f0/Fight_Network.svg/1200px-Fight_Network.svg.png",
    category: "Sports",
    country: "Canada",
    stream: "https://lnc-fight-network.tubi.video/playlist.m3u8",
    description: "Combat sports and martial arts programming."
  },

  // ── Entertainment ──
  {
    id: "pluto-comedy",
    name: "Pluto TV Comedy",
    logo: "https://images.pluto.tv/channels/5ca673f4ad95e4001338a71e/colorLogoPNG.png",
    category: "Entertainment",
    country: "USA",
    stream: "https://service-stitcher.clusters.pluto.tv/v1/stitch/embed/hls/channel/5ca673f4ad95e4001338a71e/master.m3u8",
    description: "Free comedy shows and stand-up specials."
  },
  {
    id: "filmrise-movies",
    name: "FilmRise Free Movies",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/FilmRise_logo.svg/1200px-FilmRise_logo.svg.png",
    category: "Entertainment",
    country: "USA",
    stream: "https://lnc-filmrise-free-movies.tubi.video/playlist.m3u8",
    description: "Free movies streaming around the clock."
  },
  {
    id: "fash-tv",
    name: "Fashion TV",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Fashion_TV.svg/1200px-Fashion_TV.svg.png",
    category: "Entertainment",
    country: "France",
    stream: "https://fash-fash1-1-nl.samsung.wurl.tv/manifest/playlist.m3u8",
    description: "Global fashion, models, and lifestyle content."
  },

  // ── Music ──
  {
    id: "mtv-hits",
    name: "MTV Hits",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/c/c5/MTV_Hits.svg/1200px-MTV_Hits.svg.png",
    category: "Music",
    country: "USA",
    stream: "https://lnc-mtv-hits.tubi.video/playlist.m3u8",
    description: "Non-stop music videos and music programming."
  },
  {
    id: "trace-urban",
    name: "Trace Urban",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Trace_Urban.svg/1200px-Trace_Urban.svg.png",
    category: "Music",
    country: "France",
    stream: "https://trace-urban.samsung.wurl.tv/manifest/playlist.m3u8",
    description: "Urban and hip-hop music from around the world."
  },
  {
    id: "djing-live",
    name: "DJing Live",
    logo: "https://i.imgur.com/xDJBq6r.png",
    category: "Music",
    country: "International",
    stream: "https://lnc-djing.tubi.video/playlist.m3u8",
    description: "Live DJ sets and electronic music 24/7."
  },

  // ── Documentary & Science ──
  {
    id: "nasa-tv",
    name: "NASA TV",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e5/NASA_logo.svg/1200px-NASA_logo.svg.png",
    category: "Documentary",
    country: "USA",
    stream: "https://ntv1.akamaized.net/hls/live/2014075/NASA-NTV1-HLS/master.m3u8",
    description: "Live coverage of NASA missions, launches, and ISS activities."
  },
  {
    id: "curiosity",
    name: "CuriosityStream",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/CuriosityStream_2018.svg/1200px-CuriosityStream_2018.svg.png",
    category: "Documentary",
    country: "USA",
    stream: "https://lnc-curiositystream.tubi.video/playlist.m3u8",
    description: "Award-winning science and nature documentaries."
  },
  {
    id: "history-vault",
    name: "History Vault",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/History_%28American_TV_network%29_2020.svg/1200px-History_%28American_TV_network%29_2020.svg.png",
    category: "Documentary",
    country: "USA",
    stream: "https://lnc-history-vault.tubi.video/playlist.m3u8",
    description: "Historical documentaries and archive footage."
  },

  // ── Kids ──
  {
    id: "kidoodle",
    name: "Kidoodle TV",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/Kidoodle.TV_Logo.png/1200px-Kidoodle.TV_Logo.png",
    category: "Kids",
    country: "Canada",
    stream: "https://lnc-kidoodle-tv.tubi.video/playlist.m3u8",
    description: "Safe, family-friendly content for kids."
  },
  {
    id: "toonami",
    name: "Toonami Aftermath",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/Toonami_2014.svg/1200px-Toonami_2014.svg.png",
    category: "Kids",
    country: "USA",
    stream: "https://api.new.livestream.com/accounts/26148004/events/8818748/live.m3u8",
    description: "Anime and animated series block."
  },

  // ── Lifestyle ──
  {
    id: "bon-appetit",
    name: "Bon Appetit",
    logo: "https://i.imgur.com/mR5LuNc.png",
    category: "Lifestyle",
    country: "USA",
    stream: "https://lnc-bon-appetit.tubi.video/playlist.m3u8",
    description: "Cooking shows, recipes, and food culture."
  },
  {
    id: "outdoor-tv",
    name: "Outdoor Channel",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/a/a6/Outdoor_Channel.svg/1200px-Outdoor_Channel.svg.png",
    category: "Lifestyle",
    country: "USA",
    stream: "https://lnc-outdoor.tubi.video/playlist.m3u8",
    description: "Outdoor adventures, hunting, and fishing shows."
  },

  // ── Religion ──
  {
    id: "quran-tv",
    name: "Quran TV",
    logo: "https://i.imgur.com/sC0oZ6B.png",
    category: "Religion",
    country: "Saudi Arabia",
    stream: "https://edge.taghtia.com/sa/3.m3u8",
    description: "Holy Quran recitation and Islamic programming 24/7."
  },
  {
    id: "peace-tv",
    name: "Peace TV",
    logo: "https://i.imgur.com/8F0WW0r.png",
    category: "Religion",
    country: "International",
    stream: "https://mn-nl.mncdn.com/peacetv_peacetv/peacetv/playlist.m3u8",
    description: "Islamic lectures and educational content."
  },

  // ── Business ──
  {
    id: "bloomberg",
    name: "Bloomberg TV",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Bloomberg_logo.svg/1200px-Bloomberg_logo.svg.png",
    category: "Business",
    country: "USA",
    stream: "https://www.bloomberg.com/media-manifest/streams/us.m3u8",
    description: "Financial news, market data, and business analysis."
  },

  // ── Regional / South Asian ──
  {
    id: "somoy-tv",
    name: "Somoy TV",
    logo: "https://upload.wikimedia.org/wikipedia/commons/2/29/Somoy_TV_logo.png",
    category: "News",
    country: "Bangladesh",
    stream: "https://us170.jagobd.com:447/c3VydmVyX8LJEWVfMjAyMF9saXZl/stream/34/SomoyTV/tracks-v1a1/mono.m3u8",
    description: "Leading Bengali-language news channel from Bangladesh."
  },
  {
    id: "atn-bangla",
    name: "ATN Bangla",
    logo: "https://upload.wikimedia.org/wikipedia/en/thumb/6/66/ATN_Bangla_logo.svg/1200px-ATN_Bangla_logo.svg.png",
    category: "Entertainment",
    country: "Bangladesh",
    stream: "https://us170.jagobd.com:447/c3VydmVyX8LJEWVfMjAyMF9saXZl/stream/22/ATNBangla/tracks-v1a1/mono.m3u8",
    description: "Bangladeshi entertainment and variety channel."
  },
  {
    id: "ndtv-24x7",
    name: "NDTV 24x7",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/NDTV_logo.svg/1200px-NDTV_logo.svg.png",
    category: "News",
    country: "India",
    stream: "https://ndtv24x7elemarchana.akamaized.net/hls/live/2003678/ndtv24x7/master.m3u8",
    description: "India's leading English news channel."
  },
  {
    id: "aajtak",
    name: "Aaj Tak",
    logo: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Aaj_Tak_logo.svg/1200px-Aaj_Tak_logo.svg.png",
    category: "News",
    country: "India",
    stream: "https://feeds.intoithis.com/aajtak/master.m3u8",
    description: "India's #1 Hindi news channel."
  }
];

const CATEGORIES = [
  { id: "all", name: "All Channels", icon: "fas fa-globe" },
  { id: "News", name: "News", icon: "fas fa-newspaper" },
  { id: "Sports", name: "Sports", icon: "fas fa-futbol" },
  { id: "Entertainment", name: "Entertainment", icon: "fas fa-film" },
  { id: "Music", name: "Music", icon: "fas fa-music" },
  { id: "Documentary", name: "Documentary", icon: "fas fa-microscope" },
  { id: "Kids", name: "Kids", icon: "fas fa-child" },
  { id: "Lifestyle", name: "Lifestyle", icon: "fas fa-utensils" },
  { id: "Religion", name: "Religion", icon: "fas fa-mosque" },
  { id: "Business", name: "Business", icon: "fas fa-chart-line" }
];
