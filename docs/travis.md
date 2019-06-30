## Travis CI 集成配置说明

#### 一、Travis CI 配置文件的生命周期

> 1. before_install
> 2. install：安装依赖
> 3. before_script
> 4. script：运行脚本
> 5. aftersuccess or afterfailure：script阶段执行成功或失败时执行
> 6. [OPTIONAL] before_deploy
> 7. [OPTIONAL] deploy：部署
> 8. after_script

#### 二、加密敏感信息，如`keystore`文件

- 本地安装`travis`

  ```shell
  gem install travis
  ```

- 登录`travis.org`(收费版的是`com`)

  ```shell
  travis login --org
  ```

- 加密对应文件或字符

  ```shell
  travis encrypt-file xxxx.jks --add
  ```

  如此会将加密后的字段信息生成变量配置在`travis.org`对应工程下。还有一个`*.enc`的文件生成，并会在`.travis.yml`中添加信息。

  ```shell
  ## travis 对keyStore文件加密，自动生成的，
  before_install:
  - openssl aes-256-cbc -K $encrypted_xxxxxx_key -iv $encrypted_xxxxxx_iv
  - in xxxx.jks.enc -out githubKeyStore.jks -d
  ```

- 在`travis.org`的控制台，配置变量`keysotre_pass`,`alias_name`,`alias_pass`

- 项目根目录创建`keystore.properties`文件，用于本地构建签名包