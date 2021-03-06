include 'asynchttp_v1'

/**
 *  Motion Sensor Test
 *
 *  Copyright 2017 Darren Murray
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
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
	// Subscribe to attributes, devices, locations, etc.
    subscribe(sensors, "motion", evtHandlerMotion)
}

//  Event handlers
def evtHandlerMotion(evt) {
//Create message body
Map body = [deviceName: evt.device.name,
deviceID: evt.deviceId,
lastActive: evt.isoDate,
location: evt.location.name,
stateChanged: evt.stateChange,
motion: sensors.currentValue("motion").first(),
battery: sensors.currentValue("battery").first(),
temperature: sensors.currentValue("temperature").first()]

//Log device and Capabilites
log.debug(body)
log.debug("${evt.device}Event Handler called")
sensors.capabilities.each {cap -> log.debug "This device supports the ${cap.name} capability"}

    def params = [
        uri: 'https://home-hub-59831.herokuapp.com',
        path: '/api/sensor',
        body: body
    ]
    asynchttp_v1.post(responseHandler, params)


}

def sendEvent(body) {
log.debug("test")

    def params = [
        uri: 'https://home-hub-59831.herokuapp.com',
        path: '/api/sensor',
        body: body
    ]
    asynchttp_v1.post(responseHandler, params)
}


def responseHandler(response, data) {
    log.debug "Response data: ${response.data}"
}
