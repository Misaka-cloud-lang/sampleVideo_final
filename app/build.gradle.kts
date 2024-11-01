plugins {
    alias(libs.plugins.android.application)
}


android {
    namespace = "com.example.samplevideo"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.samplevideo"
        minSdk = 17
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.gson)
    implementation(libs.picasso)
    implementation("cn.jzvd:jiaozivideoplayer:7.7.2.3300")
}
//configurations.all {
//    resolutionStrategy.eachDependency { details: DependencyResolveDetails ->
//        if (details.requested.group == "cn.jzvd" && details.requested.name == "jiaozivideoplayer") {
//            details.useTarget("${details.requested.group}:${details.requested.name}:${details.requested.version}-sources@jar")
//        }
//    }
//}
