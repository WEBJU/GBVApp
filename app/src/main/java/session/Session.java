package session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private String name,email,phone;
    private SharedPreferences preferences;

    public Session(Context context){
        preferences= PreferenceManager.getDefaultSharedPreferences(context);

    }

    public String getName() {
        name=preferences.getString("name","");
        return name;
    }

    public void setName(String name) {
       preferences.edit().putString("name",name).commit();
    }

    public String getEmail() {
        email=preferences.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        preferences.edit().putString("email",email).commit();
    }

    public String getPhone() {
        phone=preferences.getString("phone","");
        return phone;
    }

    public void setPhone(String phone) {
        preferences.edit().putString("phone",phone).commit();
    }
}
