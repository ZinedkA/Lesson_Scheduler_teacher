buildscript {
    ext {
        compose_ui_version = "1.5.0"
        room_version = "2.6.1"
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}
