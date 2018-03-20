<img src="report/images/scipio.png" alt="Scipio Africanus" style="width: 100px;"/>

# **scipio-bot**
A BWAPI (BWMirror) Scala based Starcraft Broodwar bot made for MLP.\

**Collaborators**:
- Henrique Silva
- Maria Clara Jacintho
- Gabriel Niemiec

---

## *How to run it?*
1. Install the latest 32bit [JDK][java] (version 8)
2. Download Starcraft patch 1.16.1 from [here][starcraft]
3. Download BWAPI from [here][bwapi] and install
4. Import this project to IntelliJ
5. Set up your IntelliJ JDK to the one you just downloaded
6. In ChaosLauncher (inside BWAPI's folder), tick both `Injector (RELEASE)` and `W-MODE`
7. Run the project
8. Start Starcraft via ChaosLauncher

## *How to test it?*
After opening Starcraft via ChaosLauncher, click:

    Single Player -> Expansion -> Play Custom -> Select map and race -> Play

Note that this process can be automated by copy-pasting `bwapi.ini` into `<starcraft_folder>/bwapi-data/`, inside your\
Starcraft folder.

[java]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[starcraft]: http://files.theabyss.ru/sc/starcraft.zip
[bwapi]: https://github.com/bwapi/bwapi/releases/download/v4.1.2/BWAPI_412_Setup.exe
