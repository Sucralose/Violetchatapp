plugins {
      id("com.android.application")

      id("com.google.gms.google-services")
}

android {
      namespace = "com.benavivi.violetchatapp"
      compileSdk = 33

      defaultConfig {
            applicationId = "com.benavivi.violetchatapp"
            minSdk = 26
            targetSdk = 33
            versionCode = 1
            versionName = "1.0"

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
      buildFeatures {
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
      implementation("com.google.firebase:firebase-storage")
      implementation("com.google.firebase:firebase-messaging")
      implementation("com.firebaseui:firebase-ui-storage:8.0.2")
      implementation("com.firebaseui:firebase-ui-firestore:8.0.2")

      //Okhttp - for http requests
      implementation("com.squareup.okhttp3:okhttp:4.11.0")

      //---Scaleable size units
      implementation("com.intuit.sdp:sdp-android:1.1.0")
      implementation("com.intuit.ssp:ssp-android:1.1.0")

      //---Rounded image view
      implementation("com.makeramen:roundedimageview:2.3.0")

      //--Picasso - Image loading and caching library
      implementation("com.squareup.picasso:picasso:2.8")


//      //Preference datastore - androidx
      implementation("androidx.datastore:datastore-preferences-core:1.0.0")


}