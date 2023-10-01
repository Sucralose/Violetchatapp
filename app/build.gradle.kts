plugins {
      id("com.android.application")

      id("com.google.gms.google-services")
}

android {
      namespace = "com.benavivi.violetchatapp"
      compileSdk = 33

      defaultConfig {
            applicationId = "com.benavivi.violetchatapp"
            minSdk = 27
            targetSdk = 33
            versionCode = 1
            versionName = "1.0"
            multiDexEnabled=true

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }

      buildTypes {
            release {
                  isMinifyEnabled = false
                  proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
      }
      compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
      }
      buildFeatures{
            viewBinding = true
      }
}

dependencies {

      implementation("androidx.appcompat:appcompat:1.6.1")
      implementation("com.google.android.material:material:1.9.0")
      implementation("androidx.constraintlayout:constraintlayout:2.1.4")
      testImplementation("junit:junit:4.13.2")
      androidTestImplementation("androidx.test.ext:junit:1.1.5")
      androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

      //Firebase
      implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
      implementation("com.google.firebase:firebase-messaging")
      implementation("com.google.firebase:firebase-auth")
      implementation("com.google.firebase:firebase-database")
      implementation("com.google.firebase:firebase-firestore")
//      implementation("com.google.firebase:firebase-core")




      //Scaleable size units
      implementation("com.intuit.sdp:sdp-android:1.1.0")
      implementation("com.intuit.ssp:ssp-android:1.1.0")

      //Rounded image view
      implementation("com.makeramen:roundedimageview:2.3.0")

      //Multidex
      implementation("androidx.multidex:multidex:2.0.1")

//      //Preference datastore - androidx
//      implementation("androidx.datastore:datastore-preferences-core:1.0.0")


}