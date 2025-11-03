// ------------- GetAll -------------
export const getAllBooks = async (accessToken:string) =>{
    const res = await fetch("/api/books",{
        headers:{Authorization:`Bearer ${accessToken}`},
    });
    if (res.ok){
        const response = await res.json();
        console.log(response);
        return response;
    }else {
        const message = await res.text();
        throw new Error(message || "Request could not be completed");
    }
}