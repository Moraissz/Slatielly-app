package com.example.slatielly.app.dress;

import com.example.slatielly.model.Dress;
import com.example.slatielly.model.Image;

import java.util.ArrayList;

public interface DressContract {
    interface View {
        void setDressViews(Dress dress);

        void setScreenSlideAdapter(ArrayList<Image> images);
    }

    interface Presenter {
        void loadDress(String id);
    }
}
