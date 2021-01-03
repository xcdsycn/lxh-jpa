# 学习这项技术的原因
2020-12-31，在公司接触到的项目，用Spring-data-jpa，来作项目。
当前的主流应用，还是Spring+MyBatis的应用多些。但是又不得不学习这个应用，所以就有了这个工程。

# 主要任务
1. 学会如何用
2. 对比一下，与MyBatis的差异

# 想法
1. 目前基于代码库的开发太耗时
2. 打包生成的文件太大
3. 如果有一项技术，可以把代码库中，只有使用的代码抽取出来，那就真的太好了，
可以大大缩小项项目的体积，虽然这样做对于运行时没有什么影响，但是可以使部署运维速度加快。
到时候就可以直接说，我的应用不直接依赖于任何第三方代码库。不过这些同名文件，必须重新组织一下package，避免冲突。