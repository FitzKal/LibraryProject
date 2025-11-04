import {type SubmitHandler, useForm} from "react-hook-form";
import type {BookRequest} from "../../Types/FormTypes.ts";
import {userStore} from "../../Stores/UserStore.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {postBook} from "../../Services/BookService.ts";
import {toast} from "react-toastify";

export default function PostBookForm(){
    const {register,handleSubmit,formState:{errors,isSubmitting},reset} = useForm<BookRequest>();
    const currentUser = userStore.getState().user;
    const queryClient = useQueryClient();

    //TODO: Maybe implement isSubmitting

    const onSubmit: SubmitHandler<BookRequest> = async (data) => {
        mutation.mutate(data);
    }

    const mutation = useMutation({
        mutationFn:(data:BookRequest) =>
            postBook(currentUser.accessToken,data,currentUser.username),
        onSuccess:() =>{
            toast.success("Book posted successfully");
            reset();
            queryClient.invalidateQueries({queryKey:["books"]});
        },
        onError:(error) =>{
          if (error instanceof Error){
              toast.error(error.message);
            } else {
              toast.error("Something went wrong");
            }
        }
    })

    return (
        <div className={"flex flex-col justify-center m-auto mb-5"}>
            <h1 className={"text-center mr-10 text-xl mb-3"}>Add a new book</h1>
          <div className={"flex border-2 rounded-2xl p-2 w-250 self-center bg-yellow-100"}>
              <form onSubmit={handleSubmit(onSubmit)}>
                 <div className={"flex fle gap-3 flex-wrap"}>
                     <div className={"flex flex-col"}>
                         <input{...register("title",{
                             required:"Title is required",
                             minLength:3,
                         })} type={"text"} placeholder={"title"} className={"border-2 bg-white text-center w-75"} />
                         {errors.title &&( <div className={"text-red-500 w-auto"}>{errors.title.message}</div>)}
                     </div>

                     <div className={"flex flex-col"}>
                         <input{...register("pictureSRC",{
                             required:"Picture is required",
                         })} type={"text"} placeholder={"picture URL"} className={"border-2 bg-white text-center w-75"} />
                         {errors.pictureSRC &&(<div className={"text-red-500"}>{errors.pictureSRC.message}</div>)}
                     </div>

                     <div className={"flex flex-col"}>
                         <input{...register("description",{
                             required:"Description is required",
                             minLength:10
                         })} type={"text"} placeholder={"description"} className={"border-2 bg-white text-center w-75 he overflow-hidden"} />
                         {errors.description &&(<div className={"text-red-500"}>{errors.description.message}</div>)}
                     </div>

                     <div className={"flex flex-col"}>
                         <input{...register("author",{
                             required:"Author is required",
                             minLength:3
                         })} type={"text"} placeholder={"author"} className={"border-2 bg-white text-center w-75"} />
                         {errors.author &&(<div className={"text-red-500"}>{errors.author.message}</div>)}
                     </div>
                     <label>Choose a genre: </label>
                     <select{...register("genre",{
                         required:true,
                     })} className={"bg-white border-2 max-h-lh"}>
                         <option value="FANTASY" >Fantasy</option>
                         <option value="THRILLER">Thriller</option>
                         <option value="SCIFI">Sci-Fi</option>
                         <option value="ROMANCE">Romance</option>
                     </select>
                     <button type={"submit"} className={"border-2 pr-2 pl-2 ml-1 bg-green-400 transition delay-75 ease-in-out hover:bg-green-600 max-h-7 w-30"}
                     >Post</button>
                 </div>
              </form>
          </div>
        </div>
    );
}
