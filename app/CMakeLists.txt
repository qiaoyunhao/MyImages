# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)
#####################开始#############第0_0步：引入opencv
set(lib_src_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})  #类似于定义了个宏，用lib_srcDIR代替非常长且相同的路径部分
include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)  #引入复制到../cpp路径下的头文件

##添加opencv_java-lib这个.so文件
add_library(opencv_java3-lib
                   SHARED
                   IMPORTED)
set_target_properties(#Specifies the target libary
                          opencv_java3-lib
                          PROPERTIES IMPORTED_LOCATION
                          ${lib_src_DIR}//libopencv_java3.so ##注意这里的路径用到了上面定义的宏
                        )
#################结 束##################---第0_1步见build.gradle文件
# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
###################开始##########################第1_1步：添加所链接的函数库名称，这里就是.cpp文件，及其文件路径
add_library( # Sets the name of the library.
             imgCanny

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/imgCanny.cpp )     #这就是C++程序所在的路径，自己想加C++就继续添加add_library( )

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp )
########################结束#################################
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
######################开始#################################第1_2步：添加新的library函数库名称，也就是.cpp文件名
target_link_libraries( # Specifies the target library.
                       imgCanny
                       opencv_java3-lib
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
########################结束###################################第2_0步 见MainActivity.java文件