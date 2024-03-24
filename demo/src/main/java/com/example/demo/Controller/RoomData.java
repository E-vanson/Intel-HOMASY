package com.example.demo.Controller;

public class RoomData {
    public Integer id;
    public String roomNo;

    public Integer price;

    public Boolean status;

    public String description;

    public Integer tenantId;

    public RoomData(Integer id, String roomNo, Integer price, Boolean status, String description,Integer tenantId){
        this.id= id;
        this.roomNo= roomNo;
        this.price = price;
        this.status = status;
        this.tenantId = tenantId;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getRoomNo(){
        return roomNo;
    }

    public int getPrice(){
        return price;
    }

    public boolean getStatus(){
        return status;
    }

    public String getDescription(){
        return description;
    }

    public int getTenantId(){
        return tenantId;
    }



}
