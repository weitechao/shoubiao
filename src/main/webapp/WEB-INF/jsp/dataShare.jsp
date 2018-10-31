<!-- TODO -->
Hello world!


<c:when test="${not empty heartPressureDataMap}">
    maxHeartPressure: ${heartPressureDataMap.maxHeartPressure}<br/>
    minHeartPressure: ${heartPressureDataMap.minHeartPressure}<br/>
    status: ${heartPressureDataMap.status}<br/>
    timestamp: ${heartPressureDataMap.timestamp}<br/>
</c:when>

<c:when test="${not empty heartRateDataMap}">
    heartRate: ${heartRateDataMap.heartRate}<br/>
    status: ${heartRateDataMap.status}<br/>
    timestamp: ${heartRateDataMap.timestamp}<br/>
</c:when>

<c:when test="${not empty stepDataMap}">
    step: ${stepDataMap.step}<br/>
</c:when>