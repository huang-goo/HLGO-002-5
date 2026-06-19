# RssReader 项目提示词集合

> 项目：`com.apptasticsoftware:rssreader` — Java RSS/Atom Feed 解析库  
> 核心类：`AbstractRssReader`、`RssReader`、`Channel`、`Item`、`DateTimeParser`、各扩展 FeedReader  

---

## 一、0-1 代码生成（共 20 条）

**1.** 实现一个多 Feed 聚合模块，接收多个 RSS/Atom URL，并发拉取后将所有 Item 合并，按发布时间降序排序，并根据 GUID 或标题去重，最终以 `Stream<Item>` 形式返回结果给调用方。

**2.** 基于 `AbstractRssReader` 实现一个 `JsonFeedReader`，支持解析 JSON Feed 格式（jsonfeed.org），将 JSON 中的 `items` 数组映射为标准 `Item` 对象，与已有 RSS/Atom 接口保持行为一致。

**3.** 开发一个 `FeedSubscriptionManager` 类，维护用户订阅的 Feed URL 列表，支持添加和删除订阅，并在每次调用 `refresh()` 时拉取最新数据，返回自上次刷新后新增的 Item 集合。

**4.** 实现一个 `RssFeedValidator` 工具类，对给定 URL 发起请求，检测响应是否为合法的 RSS 或 Atom 格式，验证必填字段完整性，并将所有格式问题汇总为一个校验结果对象返回。

**5.** 为 `RssReader` 开发 HTTP 代理支持配置类，允许用户在构造 `HttpClient` 时注入代理地址和认证信息，支持 HTTP/HTTPS 代理，同时保留原有连接超时和请求超时配置不变。

**6.** 实现一个 `FeedItemRanker`，基于 `ItemComparator` 提供链式 API，允许用户配置多维度排序规则（发布时间、标题字母序、来源 Feed），对聚合后的 Item 列表进行多级排序输出。

**7.** 开发一个 `FeedConverter` 工具类，将解析后的 `Channel` 和 `Item` 对象序列化为标准 RSS 2.0 XML 字符串，支持自定义编码格式，可用于程序动态生成合法 RSS Feed 文件场景。

**8.** 实现一个 `KeywordFeedFilter` 类，实现 `FeedFilter` 接口，按关键词过滤 Item，支持对标题、描述、内容字段进行包含或排除匹配，支持大小写不敏感模式，可链式组合使用。

**9.** 开发一个 `FeedStatistics` 工具类，对传入的 `Stream<Item>` 进行统计分析，输出包括 Item 总数、最早和最新发布时间、每日发布数量分布、最活跃作者等信息的统计报告对象。

**10.** 实现一个 `AtomFeedBuilder`，通过 Builder 模式逐步构建 Atom Feed，支持设置 Channel 元数据和多个 Item，最终生成合法的 Atom XML 字符串，用于服务端动态生成 Feed 的场景。

**11.** 开发一个 `FeedDiff` 工具类，接收同一 Feed 在两个时间点解析的 Item 集合，通过 GUID 比对识别新增、删除和内容变更的条目，返回差异报告对象，适用于 Feed 变更监控场景。

**12.** 为 `AbstractRssReader` 增加 `RetryPolicy` 支持，当 HTTP 请求因超时或 5xx 错误失败时，按指数退避策略进行重试，重试次数和初始间隔可配置，超出上限后向调用方抛出异常。

**13.** 基于 `ItunesFeedReader` 实现一个 `PodcastSearchService`，接受 Feed URL 和关键词，读取播客 Feed 后在 episode 标题和描述中搜索匹配内容，返回命中的 `ItunesItem` 列表。

**14.** 开发一个 `FeedExporter`，将 `Stream<Item>` 导出为 CSV 文件，列包含标题、链接、作者、发布时间和摘要，支持自定义分隔符和是否输出 BOM 头，适用于数据离线分析场景。

**15.** 实现一个 `YoutubeFeedAggregator`，接收多个 YouTube 频道 Feed URL，使用 `YoutubeFeedReader` 并发解析，汇总最新视频 Item，按上传时间排序后返回，支持限制最大返回条数。

**16.** 开发一个基于 `GeoRssFeedReader` 的地理围栏过滤器 `GeoFenceFilter`，接受中心坐标和半径参数，过滤出 `Coordinate` 在指定范围内的 Item，返回符合地理条件的 `Stream<Item>`。

**17.** 实现一个 `FeedNotificationService`，定期检查多个 Feed URL 是否有新 Item，一旦发现新条目立即通过注册的 `Listener` 接口触发回调，Listener 接收新 Item 列表作为参数处理。

**18.** 开发一个 `MediaRssThumbnailDownloader`，读取 MediaRss Feed，提取每个 Item 中的 `MediaThumbnail` URL，批量下载图片至本地指定目录，并记录每张图片下载成功或失败的日志。

**19.** 实现一个 `FeedMerger` 类，将多个来源相同主题但格式不同的 Feed（RSS、Atom、MediaRss）统一解析后合并，按发布时间去重排序，对外暴露统一的 `Stream<Item>` 接口。

**20.** 开发一个 `InvalidXmlRepairStrategy`，在 `InvalidXmlCharacterFilter` 基础上增加对常见 XML 实体编码错误（如未转义的 `&` 符号）的自动修复能力，提升对格式不规范 Feed 的解析兼容性。

---

## 二、Feature 迭代（共 20 条）

**21.** 在 `AbstractRssReader` 的 `read` 方法中增加对 HTTP 301/302 重定向的跟踪支持，允许配置最大重定向次数（默认 5），超出限制时抛出异常，并在日志中完整记录重定向链路。

**22.** 为 `RssReader` 新增 `readFromFile(Path filePath)` 和 `readFromString(String xml)` 两个重载方法，支持从本地文件或字符串内容解析 Feed，复用现有 XML 解析逻辑，不重复建立 HTTP 连接。

**23.** 在 `FeedFilter` 基础上增加 `DateRangeFilter` 实现，允许用户指定起止日期区间，只保留发布时间在区间内的 Item，支持 `ZonedDateTime` 参数并正确处理不同时区之间的转换比较。

**24.** 扩展 `DateTimeParser`，增加对非标准日期格式的自动推断能力，当已知格式均失败时遍历常见格式模板尝试解析，将首次成功的格式缓存下来，提升后续相同格式的解析性能。

**25.** 在 `AbstractRssReader` 中增加 HTTP Basic Auth 请求级支持，允许为指定 URL 配置用户名和密码，发起请求时自动附加 `Authorization` 头，适用于需要认证的私有或付费 Feed 地址。

**26.** 在 `FeedExtensionRegistry` 中新增 `addNamespaceAlias(String prefix, String uri)` 方法，支持用户注册自定义命名空间前缀，解析带命名空间的自定义扩展标签时可正确匹配和提取字段。

**27.** 为 `Item` 接口新增 `getFullText` / `setFullText` 方法，扩展 `ItemImpl` 实现，并在 `registerItemTags` 中注册 `content:encoded` 标签映射，使 Feed 中的文章全文内容可被正确读取。

**28.** 为 `AbstractRssReader` 增加 `setCollectChildNodesForTag(Set<String> tags)` 方法，允许用户在运行时动态扩展需要收集子节点文本的标签集合，而非仅限于内置的 `content` 和 `summary`。

**29.** 在 `AbstractRssReader` 中增加对 Brotli 压缩响应的解压支持，检测 `Content-Encoding: br` 响应头，使用对应解码器处理响应流，与现有 `GZIPInputStream` 逻辑并行处理不同压缩格式。

**30.** 为 `SpotifyFeedReader` 增加对 `spotify:explicit` 标签的解析支持，映射到 `SpotifyItem` 的新字段 `explicit`，同步在 `SpotifyItemData` 接口中增加对应的 getter 和 setter 方法。

**31.** 扩展 `PodcastFeedReader`，将 `podcast:funding` 从单值改为支持多条 `List<PodcastFunding>`，更新 `PodcastChannelData` 接口定义和 `PodcastChannelImpl` 实现，保证多资金来源数据不丢失。

**32.** 为 `WfwFeedReader` 增加对 `wfw:commentRss` 标签的解析，将评论 Feed URL 映射到 `WfwItem` 的新字段 `commentRssUrl`，在 `WfwItemData` 接口中增加对应 getter 和 setter 声明。

**33.** 在 `AbstractRssReader` 中增加 ETag 和 Last-Modified 缓存头支持，每次请求成功后缓存响应头，下次请求同一 URL 时附带 `If-None-Match` 和 `If-Modified-Since`，服务器 304 时返回缓存结果。

**34.** 扩展 `ItemComparator`，增加对多语言标题归一化排序的支持，先将标题转为小写并去除前导冠词（如 "the"、"a"），再按字母序比较，提升多语种 Feed 聚合时排序结果的一致性。

**35.** 在 `AbstractRssReader` 的 `registerChannelTags` 中补充 Atom `<logo>` 标签高度和宽度属性的解析，将其映射到 `Image` 对象的 `height` 和 `width` 字段，与现有 Image 解析逻辑保持一致。

**36.** 扩展 `OpenSearchFeedReader`，增加对 `openSearch:Query` 元素中 `role` 属性的解析，将其映射到 `OpenSearchQuery` 的 `role` 字段，并在 `OpenSearchChannelData` 中提供对应访问方法。

**37.** 在 `Item` 接口中增加 `getSource` / `setSource` 方法，用于存储 RSS 2.0 `<source>` 元素（含 url 属性和文本内容），在 `ItemImpl` 中实现，并在 `registerItemTags` 注册解析规则。

**38.** 为 `MediaRssFeedReader` 增加对 `media:group` 内嵌套多个 `media:content` 的批量解析，将同一 group 下的内容收集为列表，更新 `MediaGroup` 数据模型和对应的解析映射逻辑。

**39.** 扩展 `FeedFilter`，添加 `AuthorFilter` 实现，支持按作者名称精确匹配或正则表达式过滤 Item，同时支持白名单和黑名单两种模式，提供 Builder API 方便调用方灵活组合配置。

**40.** 为 `AbstractRssReader` 新增 `addResponseInterceptor(BiConsumer<HttpRequest, HttpResponse<?>> interceptor)` 方法，支持用户注入响应拦截器，便于日志记录、状态码监控等横切关注点的统一处理。

---

## 三、Bug 修复（共 20 条）

**41.** `AbstractRssReader` 在 Feed 服务器返回 204 No Content 时，代码尝试解析空响应体导致 `XMLStreamException`，请修复：检测状态码后直接返回空 Stream，而非进入 XML 解析流程抛出异常。

**42.** `DateTimeParser` 在解析含非 ASCII 字母时区缩写的日期字符串时抛出 `DateTimeParseException`，请修复：在解析前将月份名统一转换为英文再进行格式匹配，支持多语言环境下的 Feed 日期字段。

**43.** Feed XML 声明编码为 ISO-8859-1 但实际内容含 UTF-8 多字节字符时，`InvalidXmlCharacterFilter` 未处理编码不一致，导致输出乱码，请修复：增加编码检测逻辑并在读取时强制统一转换。

**44.** `AbstractRssReader` 解析未闭合 CDATA 节点时会进入无限循环，请修复：增加 CDATA 结束标记检测，超出一定长度阈值时截断内容并记录 WARNING 日志，防止解析过程卡死。

**45.** `GeoRssFeedReader` 解析 `georss:point` 时，若坐标值之间有多余空格或换行符，`split(" ")` 产生空字符串导致 `NumberFormatException`，请修复：改用正则 `split("\\s+")` 并 trim 后再解析。

**46.** `ItunesItemImpl` 解析 HH:MM:SS 时长格式时，若分秒字段超出合法范围（>59）不抛出异常而是返回错误数值，请修复：增加范围校验，非法输入时返回 `Optional.empty()` 并记录 WARNING 日志。

**47.** `AbstractRssReader` 解析含多个 `<link>` 标签的 Atom Feed 时，最后一个 link 会覆盖前面的值，导致 Channel 丢失正确的 `alternate` 链接，请修复：增加标签优先级判断，优先保留 rel="alternate" 的值。

**48.** `PodcastFeedReader` 解析 `podcast:transcript` 时若 `type` 属性缺失会产生 `NullPointerException`，请修复：读取属性值时使用 null 检查，缺失时设置合理默认值而非直接赋值到字段。

**49.** `MediaRssFeedReader` 存储 `media:thumbnail` 的 url 时，若值包含未编码空格，后续构建 URI 时抛出 `URISyntaxException`，请修复：在存储前对 URL 中的空格进行 percent-encoding 规范化处理。

**50.** `XMLInputFactorySecurity` 配置未禁用外部 DTD 实体引用，存在 XXE 注入漏洞，请修复：在 `XMLInputFactory` 配置中将 `IS_SUPPORTING_EXTERNAL_ENTITIES` 和 `SUPPORT_DTD` 均设置为 false。

**51.** `FeedFilter` 链式调用时若某个 filter 抛出 `RuntimeException`，会导致整个 Stream 终止，请修复：在 filter 调用处加入 try-catch，异常时记录 WARNING 日志并跳过该 Item，不中断后续处理。

**52.** `SlashItemImpl` 解析 `slash:comments` 时，非数字字符串（如 "N/A"）导致 `mapInteger` 返回 null，调用方直接 unbox 时 `NullPointerException`，请修复：将返回类型改为 `Optional<Integer>` 并更新调用方。

**53.** `AbstractRssReader` 中 static `EXECUTOR` 在 JVM 关闭时不会自动释放，导致测试中线程泄漏，请修复：注册 JVM Shutdown Hook，在退出时调用 `EXECUTOR.shutdown()` 并等待任务完成后再退出。

**54.** `ChannelImpl` 的 `getSkipHours` 在未添加任何值时返回 null 而非空列表，导致调用方遍历时 `NullPointerException`，请修复：初始化为 `new ArrayList<>()`，getter 改为返回 `Collections.unmodifiableList()`。

**55.** `AbstractRssReader` 解析 Feed 时若 XML 文档中存在未声明的命名空间前缀（如 `<foo:bar>`），直接抛出 `XMLStreamException` 中断解析，请修复：捕获该异常并记录日志后继续处理下一个节点。

**56.** `RssReader` 读取含 UserInfo 的 URL（如 `https://user:pass@host/feed`）时不会自动提取认证信息，请修复：解析 URL 时识别 UserInfo 部分，生成 Base64 编码的 `Authorization` 请求头并附加到请求中。

**57.** `YoutubeFeedReader` 解析 `yt:channelId` 时，若标签出现在 `<entry>` 内部而非 `<feed>` 根节点，会错误赋值给 Channel 字段，请修复：增加 XPath 路径上下文校验，仅在正确层级时执行 Channel 字段映射。

**58.** `AbstractRssReader` 解析超大 Feed 时，每次 XML 事件都触发 HashMap get，存在热路径中 lambda 重复分配问题，请排查并修复：将高频调用的 `BiConsumer` 提前缓存为局部变量，减少 GC 压力。

**59.** `DcFeedReader` 解析 `dc:subject` 时使用 `setCategory` 覆盖已有 category 值，导致数据丢失，请修复：改为调用 `addCategory` 以追加方式存储，确保多来源分类字段之间不互相覆盖。

**60.** `AbstractRssReader` 的 `createHttpClient` 在 `SSLContext` 初始化失败时吞掉了 `KeyManagementException` 并返回 null，后续使用导致 NPE，请修复：异常时抛出 `IllegalStateException` 并附上原始异常链。

---

## 四、代码理解（共 1 条）

**61.** 请详细解释 `AbstractRssReader` 中 XML 解析的完整流程，包括 Channel/Item 状态切换逻辑、标签注册机制与分发过程、子节点文本收集策略，以及 `AutoCloseStream` 如何与 HTTP 连接生命周期绑定，并指出其中潜在的线程安全隐患。

---

## 五、代码重构（共 1 条）

**62.** `AbstractRssReader` 的 `registerChannelTags` 和 `registerItemTags` 方法中存在数十行重复的 `putIfAbsent` 调用，请将标签注册逻辑重构为声明式配置表（静态 Map 常量），消除冗余代码，提升方法可读性和后续扩展的可维护性。

---

## 六、工程化（共 1 条）

**63.** 项目已从 Gradle 迁移至 Maven，请完善 `pom.xml`：补充 `maven-enforcer-plugin` 强制 Java 11 和 Maven 3.8+ 要求，并新增 GitHub Actions workflow 文件，在每次 push 时自动执行 `mvn verify` 并上传 JaCoCo 覆盖率报告至 PR 评论。

---

## 七、代码测试（共 1 条）

**64.** 为 `AbstractRssReader` 的核心解析流程编写单元测试，覆盖正常 RSS 2.0、Atom、含 CDATA 及非法 XML 字符四种场景，使用 Mockito mock `HttpClient` 避免真实网络请求，断言 `Channel` 和 `Item` 字段均正确映射，目标行覆盖率不低于 80%。

---

## 统计汇总

| 类型 | 数量 | 编号 |
|------|------|------|
| 0-1 代码生成 | 20 | 1 – 20 |
| Feature 迭代 | 20 | 21 – 40 |
| Bug 修复 | 20 | 41 – 60 |
| 代码理解 | 1 | 61 |
| 代码重构 | 1 | 62 |
| 工程化 | 1 | 63 |
| 代码测试 | 1 | 64 |
| **合计** | **64** | **1 – 64** |
