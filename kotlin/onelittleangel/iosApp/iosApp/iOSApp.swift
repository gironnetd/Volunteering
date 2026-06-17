import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        let _ = createDatabase()
        MainViewControllerKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
