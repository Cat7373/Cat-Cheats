# Cat-Xray-MOD
一个透视类 MOD。

# 使用要求:
1. 带 Forge(推荐使用最新版) 的 MC 客户端，且版本为 1.8.x。

# 快速入门:
1. 下载本 MOD 并放置到客户端的 mods 文件夹中。
2. 进入游戏后按 X 来开启/关闭透视。
3. 按 F6 来编辑配置。
4. 其他功能请在按键设置内设置热键后使用(未来将会有 GUI 开关)。

# 配置说明:
* Add / Edit Block: 增加或编辑要透视的方块：
  * BlockId: 方块的ID
  * Meta: 方块的附加值(-1 表示不检测)
  * Red-Value: 线条颜色的 红色占比
  * Green-Value: 线条颜色的 绿色占比
  * Blue-Value: 线条颜色的 蓝色占比
  * Alpha-Value: 线条颜色的 透明度
* Del Block: 删除选中的方块。
* Config
  * Radius: 透视方块半径，默认45。
  * Interval: 刷新标记的方块列表的间隔，单位0.1秒，默认50。
  * AntiAntiXrayLevel: 反假矿的启用等级，含义如下：
    * 0: 不开启反假矿功能
    * 1: 开启反假矿功能
    * 2: 比上一项透视到的矿物更多，也更可能误报
    * 3: 比上一项透视到的矿物更多，也更可能误报

# 其他功能列表
* Fullbright 夜视(同时会移除反胃 / 失明效果)。

# 感谢(字母顺序排列):
* Kradxn’s-X-ray-Mod
* [LunatriusCore](https://github.com/Lunatrius/LunatriusCore)
* [Minecraft Forge](http://files.minecraftforge.net/)
* [NotEnoughItems](https://github.com/Chicken-Bones/NotEnoughItems)
* [Schematica](https://github.com/Lunatrius/Schematica)
* [WorldEdit](https://github.com/sk89q/WorldEdit)

# TODO:
* 代码部分:
  * 增加生物透视功能。
  * 增加飞行作弊功能。
  * 增加多国语言支持。
  * 增加本地观察者模式。
  * 增加弓箭瞄准辅助。
  * 刷新显示列表单独一个线程。
  * 美化 GUI 配置页面。
  * 在 GUI 增加各个功能的开关。
  * 检查代码是否有在关闭功能时需要手动释放的资源。
  * 优化代码效率。
  * 从 MOD 列表中隐藏这个 MOD。
* 非代码部分:
  * 创建一个 Wiki。
  * 增加多国语言的README.md。
  * 在大部分功能完成后将项目更名。
