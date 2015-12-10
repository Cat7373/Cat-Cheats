# Cat-Xray-MOD
一个透视类 MOD。

# 使用要求:
1. 带 Forge(推荐使用最新版) 的 MC 客户端，且版本为 1.8.x。

# 快速入门：
1. 下载本 MOD 并放置到客户端的 mods 文件夹中。
2. 进入游戏后按 X 来开启/关闭透视。
3. 编辑 config/Cat-Xray.cfg 来设置一些透视参数，详见配置文件。
4. 每次按 X 开关透视时都会重新配置，因此更新配置后无需重启游戏。

# 配置文件:
* Radius：透视方块半径，默认45。
* Interval: 刷新标记的方块列表的间隔，单位秒，默认5。
* Blocks：要透视的方块列表，每行一条，以空格为分割，每项含义如下：
  * 1: 方块的名称(区分大小写)
  * 2: 方块的附加值(-1 表示不检测)
  * 3: 线条颜色的 R 值
  * 4: 线条颜色的 G 值
  * 5: 线条颜色的 B 值
  * 6: 线条颜色的 A 值
  * 后面可以加空格随便写些什么，比如这一条的作用。
  * 以 // 开头的行为注释，可以随便写什么，或者暂时禁用某一条。
* AntiAntiXrayLevel：反假矿的启用等级(0 ~ 3)，含义如下：
  * 0: 不开启反假矿功能
  * 1: 开启反假矿功能
  * 2: 比上一项透视到的矿物更多，也更可能误报
  * 3: 比上一项透视到的矿物更多，也更可能误报

# 感谢(字母顺序排列):
* Kradxn’s-X-ray-Mod
* [LunatriusCore](https://github.com/Lunatrius/LunatriusCore)
* [Minecraft Forge](http://files.minecraftforge.net/)
* [Schematica](https://github.com/Lunatrius/Schematica)
* [WorldEdit](https://github.com/sk89q/WorldEdit)

# TODO：
* 创建一个 Wiki。
* 制作一个 GUI 配置页。
* 增加夜视功能。
* 增加生物透视功能。
* 增加飞行作弊功能。
* 整理杂乱的代码。
* 刷新显示列表单独一个线程。
* 增加多国语言支持。
