function showData(message) {
    const newRow = document.createElement('div');
    newRow.innerText = JSON.stringify(message);
    document.querySelector('#data').appendChild(newRow);
}

function convertToF(celsius) {
    return celsius * 9/5 + 32
}

function updateTemp(temp) {
    document.querySelector('#currentTemp').innerText = temp.toFixed(2) + "° F";
}

function updateHumidity(humidity) {
    document.querySelector('#humidity').innerText = humidity.toFixed(2) + "%";
}

function updateLight(value) {
    document.querySelector('#light').innerText = value;
}

function updateGyro(data) {
    document.querySelector('#gyro').innerText = `X: ${data.gyro.x.toFixed(3)} Y: ${data.gyro.y.toFixed(3)} Z: ${data.gyro.z.toFixed(3)} `;
    document.querySelector('#accelerometer').innerText = `X: ${data.accelerometer.x.toFixed(3)} Y: ${data.accelerometer.y.toFixed(3)} Z: ${data.accelerometer.z.toFixed(3)}`
}

function connect() {

    const stompClient = new StompJs.Client({
        brokerURL: `ws://${window.location.host}/ws`,
        onConnect: () => {
            stompClient.subscribe('/topic/temperature', message => {
                    // showData(JSON.parse(message.body));
                    console.log(`Received: ${message.body}`)
                    const data = JSON.parse(message.body);
                    if(data && data.bme680) {
                        updateTemp(convertToF(data.bme680.temperature));
                        updateHumidity(data.bme680.humidity)
                    }
                    if(data && data.gyro) {
                        updateGyro(data);
                    }
                    if(data && data.light) {
                        updateLight(data.light)
                    }
                }
            );
        },
    });
    stompClient.activate();
}

document.addEventListener('DOMContentLoaded', () => connect());
