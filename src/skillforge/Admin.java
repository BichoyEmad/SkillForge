/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
public class Admin extends User {
    public Admin() {
        super();
        this.role = "admin";
    }

    public Admin(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "admin");
    }
}




