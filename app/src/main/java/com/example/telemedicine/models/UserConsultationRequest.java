package com.example.telemedicine.models;

public class UserConsultationRequest
{
    private static int consId;
    private static String name, disease,
            address, description;
    private static int docId;
    private static boolean isConfirmed;

    public static int getConsId()
    {
        return consId;
    }

    public static void setConsId(int consId)
    {
        UserConsultationRequest.consId = consId;
    }

    public static String getName()
    {
        return name;
    }

    public static void setName(String name)
    {
        UserConsultationRequest.name = name;
    }

    public static String getDisease()
    {
        return disease;
    }

    public static void setDisease(String disease)
    {
        UserConsultationRequest.disease = disease;
    }

    public static String getDescription()
    {
        return description;
    }

    public static void setDescription(String description)
    {
        UserConsultationRequest.description = description;
    }

    public static String getAddress()
    {
        return address;
    }

    public static void setAddress(String address)
    {
        UserConsultationRequest.address = address;
    }

    public static int getDocId()
    {
        return docId;
    }

    public static void setDocId(int docId)
    {
        UserConsultationRequest.docId = docId;
    }

    public static boolean isConfirmed()
    {
        return isConfirmed;
    }

    public static void setIsConfirmed(boolean isConfirmed)
    {
        UserConsultationRequest.isConfirmed = isConfirmed;
    }
}
