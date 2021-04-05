package com.example.recyclerview_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ссылка на адаптер, класс который знает всё о модели и дёргает методы холдера
    private PersonAdapter mAdapter;
    //ссылка на вьюшку из представления
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Находим ссылку на контейнер - виджет
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //LinearLayoutManager занимается размещением объектов на экране и прокруткой
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Подготавливаем армию клонов
        List<CloneFactory.Person> personList = CloneFactory.getCloneList();
        //Создаём экземпляр адаптера и передаём ему под командование наших клонов. Далее руководит ими он
        mAdapter = new PersonAdapter(personList);
        //Назначаем вьюхе адаптером наш экземпляр PersonAdapter
        mRecyclerView.setAdapter(mAdapter);
    }

    /*Класс PersonHolder занят тем, что держит на готове ссылки на элементы виджетов,
        которые он с радостью наполнит данными из объекта модели в методе bindCrimе.
        Этот класс используется только адаптером в коде ниже, адаптер дёргает его и поручает
        грязную работу по заполнению виджетов*/

    private class PersonHolder extends RecyclerView.ViewHolder {
        private TextView mPersonNameTextView;
        private TextView mPersonAdressTextView;
        private TextView mPersonSexTextView;
        private TextView mPersonAgeTextView;
        private CloneFactory.Person mPerson;

        public PersonHolder(View itemView) {
            super(itemView);
            mPersonNameTextView = (TextView) itemView.findViewById(R.id.personNameView);
            mPersonAdressTextView = (TextView) itemView.findViewById(R.id.personAdressView);
            mPersonSexTextView = (TextView) itemView.findViewById(R.id.personSexView);
            mPersonAgeTextView = (TextView) itemView.findViewById(R.id.personAgeView);
        }


        //Метод, связывающий ранее добытые в конструкторе ссылки с данными модели
        public void bindCrime(CloneFactory.Person person) {
            mPerson = person;
            mPersonNameTextView.setText(mPerson.getName());
            mPersonAdressTextView.setText(mPerson.getAdress());
            mPersonAgeTextView.setText("" + mPerson.getAge());
            if (mPerson.isSex()) {
                mPersonSexTextView.setText("Мужчина");
            } else {
                mPersonSexTextView.setText("Женщина");
            }

        }
    }

    //Наш адаптер, мост между фабрикой клонов и выводом их на экран.
    //Его методы будет дёргать LinearLayoutManager, назныченный вьюшке
    //RecyclerView в методе onCreate нашей активити
    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {

        private List<CloneFactory.Person> mPersons;

        public PersonAdapter(List<CloneFactory.Person> persons) {
            mPersons = persons;
        }


        //Создаёт пустую вьюшку,оборачивает её в PersonHolder.
        //Дальше забота по наполнению этой вьюшки ложиться именно на объект PersonHolder'а
        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(R.layout.list_item_person, parent, false);
            return new PersonHolder(view);

        }

        //Дёргает метод холдера при выводе нового элемента списка на экран,
        //передавая ему актуальный объект модели для разбора и представления
        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            CloneFactory.Person person = mPersons.get(position);
            holder.bindCrime(person);

        }

        //Возвращает размер хранилища моделей
        @Override
        public int getItemCount() {
            return mPersons.size();
        }
    }
}