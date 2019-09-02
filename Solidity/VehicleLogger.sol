pragma solidity ^0.5.1;
contract VehicleLogger { 

    struct mylog { // Struct
        string time;
        string message;
    }
    
    struct vehicleInfo
    {
        string VIN;
        mylog[] logs;
    }
    
    mapping(address => vehicleInfo) info;
    
    function getLog(uint count) public view returns (string memory outstring, string memory outstring2)
    {
        outstring = info[msg.sender].logs[info[msg.sender].logs.length-count].message;
        outstring2 = info[msg.sender].logs[info[msg.sender].logs.length-count].time;
    }
    
    function setLog(string memory log, string memory date) public 
    {
        
        info[msg.sender].logs.push(mylog(date,log));
    }
    
    function setVIN(string memory vin) public
    {
        info[msg.sender].VIN = vin;
    }
    
    function getVIN() public view returns (string memory outstring)
    {
        outstring = info[msg.sender].VIN;
    }
}