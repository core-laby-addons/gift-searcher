plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "org.example"
version = providers.environmentVariable("VERSION").getOrElse("1.0.0")

labyMod {
    defaultPackageName = "de.einsjustin.giftsearcher" //change this to your main package name (used by all modules)

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    //devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "giftsearcher"
        displayName = "Gift Searcher"
        author = "EinsJustin"
        description = "Example Description"
        minecraftVersion = "1.8.9,1.21.4"
        version = rootProject.version.toString()

        addon("labyswaypoints", true)
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}