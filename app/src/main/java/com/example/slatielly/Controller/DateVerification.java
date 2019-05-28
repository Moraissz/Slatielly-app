package com.example.slatielly.Controller;

import java.util.Date;

public class DateVerification
{
    public static boolean compareDates (Date dateStart, Date datefinish, Date comparation1, Date comparation2)
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
