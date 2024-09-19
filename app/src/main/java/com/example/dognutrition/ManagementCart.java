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
        database.putListObject("CartList", listFood);
        Toast.makeText(context,"Added To Your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Popular> getListCart(){
        return  database.getListObject("CartList");
    }

    public void plusNumberFood(ArrayList<Popular>listFood, int position, ChangeNumberItemListener changeNumberItemListener){
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart()+1);
        database.putListObject("CartList",listFood);
        changeNumberItemListener.changed();
    }
    public void minusNumberFood(ArrayList<Popular>listFood, int position, ChangeNumberItemListener changeNumberItemListener){
        if(listFood.get(position).getNumberInCart() == 1){
            listFood.remove(position);
        }else{
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart()-1);
        }
        database.putListObject("CartList",listFood);
        changeNumberItemListener.changed();
    }
    public Double getTotalFee(){
        ArrayList<Popular> listFood = getListCart();
        double fee = 0;
        for (int i = 0; i < listFood.size(); i++){
            fee = fee + (listFood.get(i).getFee() * listFood.get(i).getNumberInCart());
        }
        return  fee;
    }
}

