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



#### 三、`travis ci `关联`sonar cloud`

1. 在`project`的`build.gradle`的文件内配置加入

   ```groovy
   //......
   //sonar cloud 的配置，plugins似乎只能在buildScript之后配置
   plugins {
       id "org.sonarqube" version "2.7.1"
   }
   //......
   ```

2. 在项目根目录配置`sonar-project.properties`

   ```properties
   sonar.projectKey=zhiwei1990_android-jetpack-demo
   sonar.projectName=Android Jetpack Demo
   sonar.projectVersion=1.1.2
   # =====================================================
   #   Meta-data for the project
   # =====================================================
   sonar.links.homepage=https://github.com/zhiwei1990/android-jetpack-demo
   sonar.links.ci=https://travis-ci.org/zhiwei1990/android-jetpack-demo
   sonar.links.scm=https://github.com/zhiwei1990/android-jetpack-demo
   sonar.links.issue=https://github.com/zhiwei1990/android-jetpack-demo/issues
   # =====================================================
   #   Properties that will be shared amongst all modules
   # =====================================================
   # sonar.sources指向Java代码目录
   sonar.androidTest=app/src/androidTest
   sonar.sources=app/src/main
   sonar.tests=app/src/test
   #binaries 指向编译class文件classes
   sonar.java.binaries=app/build/intermediates
   sonar.sourceEncoding=UTF-8
   sonar.profile=AndroidLint
   ```

3. 在`travis.org`对应project下配置`sonar`的`key`,参照https://docs.travis-ci.com/user/sonarcloud/中`Inspecting code with the SonarQube Scanner`

4. 最后是成品的`.travis.yml`

   ```yaml
   language: android
   sudo: required
   dist: trusty
   jdk: oraclejdk8
   
   addons:
     sonarcloud:
       organization: "zhiwei1990-github"
       token:
         secure: "Ev02Xqtdw8ddUdnfxkoz7zJ2jLHO7o5nmt5KrATMLj5pbKLsJo0Fc+dZuLhxtqJ1iaAoLf85gD32WBMA5ojbFzNNyFshgocMgHSf56xektBLhNZ1BXsA4bERxVpAKv+WdX9JiKfVm5n5b75oqN1o4QEFr9YYhGI4wlbnl3W0OTOSRE9bK/7EwB79nhcOidOctwIC9UI6lRY5CcGwUso2AzdfKCgtJ5RMz5pmOoZGZJfB8Vl04R8ohqUHjIJsLwhSrt+/xF/vMj2RapGF409NQY4q9Tz9f++OTc35oCZoPkHKRPxc8cc6qL/PtErAFa9u9cFslq4wis2xmxQrZ+2d2hWtMQIIfkxkkwZTst/EkQDLMl8NMqWkm3y9nRbhHKJkklDzIBjN3ZmSYlXuX1ZG29HTGXous0g1DuyYCL45jI9d/4FRJp7IMxfy+5zC6aABV/bGZijKLg/98p6plDx0xzy/PbL6ZofzK7it2ZmuGu+2uedgLGKf559gGz2kPjaLT7mwnjufGtb2QXgoJ2uvcge8cxjFCFFSs2eq/JsT0wcwObwoR43gOHJJNbF6zcrlqm2tB8bOah/wtjuLTwZ9xwTSPwJb1WglXwd69pCys8Sp0OMMddgk34JQBSXgwuNFhENjBOJkyjjKyHBL0QmBo/1bvKVWmCRXVXWl3WwhqLQ="
   
   before_cache:
     - rm -f  $HOME/.gradle/caches/modules-2/modules-2.lock
     - rm -rf $HOME/.gradle/caches/*/plugin-resolution/
   
   cache:
     directories:
       - '$HOME/.gradle/caches/'
       - '$HOME/.gradle/wrapper/'
       - '$HOME/.android/build-cache'
       - '$HOME/.sonar/cache'
   
   env:
     global:
       - ANDROID_API=29
       - ANDROID_BUILD_TOOLS=29.0.0
   
   android:
     components:
       - tools
       - tools # Running this twice get's the latest build tools (https://github.com/codepath/android_guides/wiki/Setting-up-Travis-CI)
       - platform-tools
       - android-${ANDROID_API}
       - build-tools-${ANDROID_BUILD_TOOLS}
       - add-on
       - extra
     licenses:
       - 'android-sdk-preview-license-52d11cd2'
       - 'android-sdk-license-.+'
       - 'google-gdk-license-.+'
   
   script:
     - chmod +x ./gradlew
     - "./gradlew clean test build"
     - sonar-scanner
   ```

如此，根据在`travis`上配置的构建策略，就会在推送代码到github后，根据策略来构建了，成功的话，还会连带`sonar cloud`分析代码。