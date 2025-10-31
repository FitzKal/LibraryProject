// ------------- LOGIN -------------
import type {UserAuthRequest} from "../Types/User.ts";

export const userLogin = async (data : UserAuthRequest) =>{
    const res = await fetch("/api/authenticate/login",{
        method:'POST',
        headers:{
            "Content-Type" : "application/json",
        },
        body:JSON.stringify(data)
    });
    if (res.ok){
        const response =  await res.json();
        console.log(response);
        return response;
    }else{
        const text = await res.text();
        throw new Error(text||"Request could not be completed!");
    }

}
