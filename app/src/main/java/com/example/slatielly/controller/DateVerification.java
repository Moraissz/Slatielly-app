package com.example.slatielly.controller;

import java.sql.Timestamp;

public class DateVerification
{
    public static boolean comparardatas (Timestamp dateStart, Timestamp datefinish, Timestamp comparation1, Timestamp comparation2)
    {
        boolean estado = false;

        if(dateStart.before(comparation1) && datefinish.before(comparation1))
        {
            return true;
        }
        if(dateStart.after(comparation2) && datefinish.after(comparation2))
        {
            return true;
        }
        return estado;
    }
}
