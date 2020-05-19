package com.example.walk_in_clinic;

import androidx.annotation.Nullable;

class Service extends android.app.Activity{

    //instance variables
    String serviceName;
    String offeredBy;

    public Service(String serviceName, String offeredBy){
        this.serviceName=serviceName;
        this.offeredBy = offeredBy;
    }

    String getServiceName(){
        return serviceName;
    }
    String getOfferedBy(){
        return offeredBy;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj==null){
            return false;
        }
        else if(this.getClass() != obj.getClass()){
            return false;
        }else{
            Service other = (Service)obj;
            if(!serviceName.equals(other.serviceName)){
                return false;
            }
            else return offeredBy.equals(other.offeredBy);
        }
    }
}
