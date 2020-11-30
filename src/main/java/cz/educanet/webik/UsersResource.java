package cz.educanet.webik;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("users") //localhost:8080/nazev-appky/api/users
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    private static List<Users> names = new ArrayList<Users>();

    @GET
    public Response getAll() {
        return Response.ok(names).build();
    }

    private boolean BoolUser(Users user) {
        for(int i = 0; i < names.size(); i++) {
            if(names.get(i).getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @PUT
    @Path(("/{{username}}"))
    public Object edit(Users user, String username, String newUsername) {

        Users tempUser = new Users(username, "");
        if(BoolUser(tempUser)) {
            for(int i = 0; i < names.size(); i++) {
                if(names.get(i).getUsername().equals(user.getUsername())) {
                    names.get(i).renameUser(newUsername);
                    return Response.ok("Uspesne si prejmenoval uzivatele").build();
                }
            }
        } else {
            return Response.status(Response.Status.valueOf("Uzivatel neexisutje")).build();
        }
        return Response.status(Response.Status.valueOf("Neco je spatne"));
    }

    @POST
    public Response createUser(String username, String password) {

        Users tempUser = new Users(username, password);

        if(BoolUser(tempUser)) {
            return Response.status(Response.Status.valueOf("Uzivatel ktereho se snazis vytvorit uz existuje")).build();
        } else {
            names.add(tempUser);
            return Response.ok("Uspesne si zaregistroval uzivatele").build();
        }
    }

    @DELETE
    public Response delete(Users user){
        if(BoolUser(user)) {
            names.remove(user);
            return Response.ok("Uspesne si odstranil uzivatele").build();
        } else {
            return Response.status(Response.Status.valueOf("Uzivatel neexistuje")).build();
        }
    }


}
