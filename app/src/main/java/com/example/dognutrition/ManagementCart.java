package com.example.dognutrition;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private Database database;

    public ManagementCart(Context context) {
        this.context = context;
        this.database = new Database(context);
    }
    public void insertFood(Popular item){
        ArrayList<Popular> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++){
            if(listFood.get(i).getTitle().equals(item.getTitle())){
                existAlready = true;
                n = i;
                break;
            }
        }

        if(existAlready){
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        }else {
            listFood.add(item);
        }
        database.putListObject("CardList", listFood);
        Toast.makeText(context,"Added To Your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Popular> getListCart(){
        return  database.getListObject("CartList");
    }
}

