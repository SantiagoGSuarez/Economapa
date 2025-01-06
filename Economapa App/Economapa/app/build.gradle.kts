plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.economapa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.economapa"
        minSdk = 24
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")//Bibilioteca para comunicarse con la API
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")//Ayuda a la biblioteca a convertir datos directo
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation("androidx.activity:activity:1.9.0")//Ayuda a cargar imagenes por url
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.appcompat:appcompat:latest_version")
    implementation ("com.google.android.material:material:latest_version")
    implementation ("org.osmdroid:osmdroid-android:6.1.10")//Open Street Map

}