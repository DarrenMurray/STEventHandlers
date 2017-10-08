definition(
    name: "Motion Sensor Test",
    namespace: "DarrenMurray",
    author: "Darren Murray",
    description: "Test App",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Select motion sensors") {
        input("sensors", "capability.motionSensor",
                          required: true, multiple: true)
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
 // Subscribe to motion device
    subscribe(sensors, "motion", evtHandlerMotion)
}

//  Event handlers
def evtHandlerMotion(evt) {
    log.debug("Motion Event Handler called")
}
