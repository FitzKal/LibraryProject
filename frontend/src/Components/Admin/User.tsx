import type {userProp} from "../../Types/User.ts";

export default function User(prop:userProp){
    return(
        <div className={"flex flex-row justify-around"}>
            <p>{prop.userInfo.userId}</p>
            <p>{prop.userInfo.username}</p>
            <p>{prop.userInfo.role}</p>
        </div>
    );
}