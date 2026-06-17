import Foundation

func createDatabase() -> Bool {
    guard let file = Bundle.main.path(forResource: "default", ofType: "realm") else {
        fatalError("Could not find file in Bundle")
    }

    guard let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first else {
        fatalError("Could not access documents directory when setting up Realm")
    }

    let realmDirectoryPath = documentsDirectory.appendingPathComponent("realm")

    do {
       try FileManager.default.createDirectory(atPath: realmDirectoryPath.path, withIntermediateDirectories: true, attributes: [.protectionKey: FileProtectionType.none])
    } catch {
       fatalError("Error creating directory: \(error.localizedDescription)")
    }

    let fullDestPath = NSURL(fileURLWithPath: realmDirectoryPath.path).appendingPathComponent("default.realm")
    let fullDestPathString = fullDestPath?.path

    do {
       if !FileManager.default.fileExists(atPath: fullDestPath!.path) {
          try FileManager.default.copyItem(atPath: file, toPath: fullDestPath!.path)
       }
    } catch {
       fatalError("Failed to load 'in_memory' Realm: \(error)")
    }

    return true
}
