import {type SubmitHandler, useForm} from "react-hook-form";
import type {AuthFormFields} from "../../Types/FormTypes.ts";
import {useMutation} from "@tanstack/react-query";
import type {UserAuthRequest} from "../../Types/User.ts";
import {userRegister} from "../../Services/AuthService.ts";
import {userStore} from "../../Stores/UserStore.ts";
import {toast} from "react-toastify";
import {Link, useNavigate} from "react-router-dom";

export default function RegisterForm(){
    const {register,handleSubmit,formState:{errors,isSubmitting}} = useForm<AuthFormFields>();

    const onSubmit: SubmitHandler<AuthFormFields> = async (data) =>{
        mutation.mutate(data);
    }

    const mutation = useMutation({
        mutationFn:(data:UserAuthRequest)=>
            userRegister(data),
        onSuccess:(result,variable) =>{
            userStore.getState().stateLogin({
                accessToken: result.accessToken,
                username: variable.username,
                role: result.role
            })
            navigate("/dashboard/home");
            toast.success("Register Successful");
        },
        onError:(error) =>{
            if (error instanceof Error){
                toast.error(error.message);
            }else {
                toast.error("Something went wrong");
            }
        }
    })

    const navigate = useNavigate();
    return(
        <div className={"container"}>
            <h1 className={"HeaderText text-4xl font-semibold"}>Register</h1>
            <div className={"FormContainer"}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={"formBody"}>
                        <input {...register("username",{
                            required : "Username is required",
                            minLength:{
                                value: 4,
                                message: "The username should be at least 4 characters long"
                            }
                        })} type={"text"} placeholder={"Username"} className={"textInput bg-white"}/>
                        {errors.username &&( <div className={"text-red-500"}>{errors.username.message}</div>)}
                        <input {...register("password",{
                            required :"Password is required",
                            minLength:{
                                value: 8,
                                message: "The given password is too weak"
                            }
                        })} type={"password"} placeholder={"Password"} className={"textInput"}/>
                        {errors.password &&( <div className={"text-red-500"}>{errors.password.message}</div>)}
                        <button type={"submit"} disabled={isSubmitting} className={"submitButton"}>
                            {isSubmitting ? "Loading..." : "Submit"}
                        </button>
                    </div>
                </form>
            </div>
            <Link className={"redirectAuthLink text-blue-600"} to={"/"}>Already have an account? Sign in!</Link>
        </div>
    );
}