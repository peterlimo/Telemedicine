package com.example.telemedicine.models;

public class User
{
    private static String fullName, birthday,
            email, phone, address, username,
            password, base64photo, token;

    public User()
    {

    }

    public static String getFullName()
    {
        return fullName;
    }

    public static void setFullName(String fullName)
    {
        User.fullName = fullName;
    }

    public static String getBirthday()
    {
        return birthday;
    }

    public static void setBirthday(String birthday)
    {
        User.birthday = birthday;
    }

    public static String getEmail()
    {
        return email;
    }

    public static void setEmail(String email)
    {
        User.email = email;
    }

    public static String getPhone()
    {
        return phone;
    }

    public static void setPhone(String phone)
    {
        User.phone = phone;
    }

    public static String getAddress()
    {
        return address;
    }

    public static void setAddress(String address)
    {
        User.address = address;
    }

    public static String getUsername()
    {
        return username;
    }

    public static void setUsername(String username)
    {
        User.username = username;
    }

    public static String getPassword()
    {
        return password;
    }

    public static void setPassword(String password)
    {
        User.password = password;
    }

    public static String getBase64photo()
    {
        return base64photo;
    }

    public static void setBase64photo(String base64photo)
    {
        User.base64photo = base64photo;
    }

    public static String getToken()
    {
        return token;
    }

    public static void setToken(String token)
    {
        User.token = token;
    }
}
