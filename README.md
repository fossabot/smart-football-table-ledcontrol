# ledcontrol

Java program that receives events via MQTT and displays scenes on a connected LED stripe. 
The LED stripe can be connected using a serial link. The program writes TPM2 frames to the serial link which will then be interpreted by a TPM2 device. The TPM2 device can be a simple µController like an Arduino or an ESP8266. 
However, the Arduino seems to be not fast enough to manage the data send since it is very limited both in processing speed and the size of the serial buffer. 

## MQTT messages
| topic      | Description                  | Example payload     |  Implemented |
| ---------- | ---------------------------- |-------------------- |------------- |
| score      | The teams' scores            | { "score": { 0, 3 } | ✔            |
| foul       | Some foul has happened       | -                   | ✔            |
| ~~winner~~     | ~~A team has won the match~~     | ~~{ "winner": 0 }~~     | ~~✔~~            |
| gameover   | A match ended                | { "winner": 0 }     | X            |
| idle       | Is there action on the table | { "idle": true }    | X            |
