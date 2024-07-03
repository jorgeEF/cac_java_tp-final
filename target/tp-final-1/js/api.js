document.getElementById('submitButton').addEventListener('click', function() {
    fetchWeather();
});

function fetchWeather() {
    const city = document.getElementById('cityInput').value;
    const url = `https://goweather.herokuapp.com/weather/${city}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta de la API');
            }
            return response.json();
        })
        .then(data => {
            // Procesar los datos de la respuesta
            const nodata = 'Información no disponible en este momento';
            let temperatura = '';
            let viento = '';
            let descripcion = '';
            let pronostico = [];

            if(data.temperature !== ''){
                temperatura = data.temperature;
                viento = data.wind;
                descripcion = data.description;
                pronostico = data.forecast;
            } else {
                temperatura = nodata;
                viento = nodata;
                descripcion = nodata;
                pronostico = [{
                    "day": "No disponible",
                    "temperature": "No disponible",
                    "wind": "No disponible"
                    }]
            }

            // Mostrar los datos en el HTML
            const weatherDataElement = document.getElementById('weather-data');
            weatherDataElement.innerHTML = `
                <p>Temperatura actual: ${temperatura}</p>
                <p>Viento: ${viento}</p>
                <p>Descripción: ${descripcion}</p>
            `;

            // Mostrar la previsión del tiempo en una tabla
            const forecastTable = document.createElement('table');
            forecastTable.innerHTML = `
                <tr>
                    <th>Día</th>
                    <th>Temperatura</th>
                    <th>Viento</th>
                </tr>
            `;
            pronostico.forEach(day => {
                forecastTable.innerHTML += `
                    <tr>
                        <td>${day.day}</td>
                        <td>${day.temperature}</td>
                        <td>${day.wind}</td>
                    </tr>
                `;
            });
            weatherDataElement.appendChild(forecastTable);
        })
        .catch(error => {
            console.error('Error al obtener los datos del clima', error);
        });
}
