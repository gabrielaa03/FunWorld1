package com.gabrielaangebrandt.funworld.picado_activity.presenter;

import android.os.Handler;

import com.gabrielaangebrandt.funworld.base.BaseImpl;
import com.gabrielaangebrandt.funworld.picado_activity.PicadoContract;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PicadoPresenterImpl extends BaseImpl implements PicadoContract.PicadoPresenter {
    PicadoContract.PicadoView view;
    private List<String> cityNames;
    private Random rand = new Random();
    private Map<String, String> hashMapLatLngOfCities = new HashMap<>();
    private double score = 0;
    private int counter = 0;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    private final Handler handler = new Handler();
    private int timer = 0;

    public PicadoPresenterImpl(PicadoContract.PicadoView view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        chooseCity(rand.nextInt(50));
        fillUpHashMap();
        addObserver(Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                timer += 1000;
                view.getTime(simpleDateFormat.format(timer));
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        }));
    }

    @Override
    public void onStop() {
        disposeCompositeD();
    }

    public void chooseCity(int number) {
        cityNames = Arrays.asList(
                "Ankara", "Tirana", "Andorra la Vella", "Yerevan", "Baku", "Vienna", "Belgrade", "Zagreb", "Rome", "Sarajevo", "Moscow",
                "Minsk", "Sofia", "Brussels", "Copenhagen", "Nicosia", "Prague", "Paris", "London",
                "Vatican City", "Bucharest", "Tallinn", "Helsinki", "Athens", "Berlin", "Budapest", "Pristina",
                "Podgorica", "Reykyavik", "Astana", "Riga", "Vaduz", "Vilnius", "Valletta", "Skopje",
                "Chişinău", "Luxembourg", "Monaco", "Kiev", "Bern", "Ljubljana", "Amsterdam", "Oslo",
                "Lisabon", "Madrid", "Stockholm", "Bratislava", "Warsaw", "City of San Marino", "Dublin");
        view.sendCityName(cityNames.get(number));
    }

    public String[] showRealCoordinatesOfCity(String cityName) {
        hashMapLatLngOfCities.get(cityName);
        return hashMapLatLngOfCities.get(cityName).split(",");
    }

    public void checkIfCoordinatesAreCorrect(String coordinates, String city, long timeInLong) {
        hashMapLatLngOfCities.get(city);
        String[] trueValueParts = hashMapLatLngOfCities.get(city).split(",");
        String[] playersValueParts = coordinates.split(",");
        double scoreDouble = Math.abs(Double.parseDouble(trueValueParts[0]) - Double.parseDouble(playersValueParts[0]) + Math.abs(Double.parseDouble(trueValueParts[1]) - Double.parseDouble(playersValueParts[1])));
        score = score + scoreDouble;
        counter++;
        if (counter == 10) {
            timer = 0;
            view.showScore(score + timeInLong);
            counter = 0;
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chooseCity(rand.nextInt(50));
                }
            }, 500);
        }
    }

    private void fillUpHashMap() {
        hashMapLatLngOfCities.put("Tirana", "41.3275,19.81889");
        hashMapLatLngOfCities.put("Andorra la Vella", "42.50779,1.52109");
        hashMapLatLngOfCities.put("Yerevan", "40.177200,44.503490");
        hashMapLatLngOfCities.put("Vienna", "48.20849,16.37208");
        hashMapLatLngOfCities.put("Baku", "40.409264,49.867092");
        hashMapLatLngOfCities.put("Minsk", "53.9,27.56667");
        hashMapLatLngOfCities.put("Brussels", "50.85045,4.34878");
        hashMapLatLngOfCities.put("Sarajevo", "43.84864,18.35644");
        hashMapLatLngOfCities.put("Sofia", "42.69751,23.32415");
        hashMapLatLngOfCities.put("Zagreb", "45.81444,15.97798");
        hashMapLatLngOfCities.put("Nicosia", "35.185566,33.382275");
        hashMapLatLngOfCities.put("Prague", "50.08804,14.42076");
        hashMapLatLngOfCities.put("Copenhagen", "55.67594,12.56553");
        hashMapLatLngOfCities.put("Tallinn", "59.43696,24.75353");
        hashMapLatLngOfCities.put("Helsinki", "60.16952,24.93545");
        hashMapLatLngOfCities.put("Paris", "48.85341,2.3488");
        hashMapLatLngOfCities.put("Zagreb", "45.81444,15.97798");
        hashMapLatLngOfCities.put("Athens", "37.97945,23.71622");
        hashMapLatLngOfCities.put("Berlin", "52.52437,13.41053");
        hashMapLatLngOfCities.put("Budapest", "47.49801,19.03991");
        hashMapLatLngOfCities.put("Reykyavik", "64.13548,-21.89541");
        hashMapLatLngOfCities.put("Belgrade", "44.787197,20.457273");
        hashMapLatLngOfCities.put("Rome", "41.89193,12.51133");
        hashMapLatLngOfCities.put("Pristina", "42.67272,21.16688");
        hashMapLatLngOfCities.put("Astana", "51.1801,71.44598");
        hashMapLatLngOfCities.put("Riga", "56.946,24.10589");
        hashMapLatLngOfCities.put("Vaduz", "47.14151,9.52154");
        hashMapLatLngOfCities.put("Vilnius", "54.68916,25.2798");
        hashMapLatLngOfCities.put("Luxembourg", "49.61167,6.13");
        hashMapLatLngOfCities.put("Skopje", "41.99646,21.43141");
        hashMapLatLngOfCities.put("Valletta", "35.89972,14.51472");
        hashMapLatLngOfCities.put("Chişinău", "47.00556,28.8575");
        hashMapLatLngOfCities.put("Monaco", "43.73333,7.41667");
        hashMapLatLngOfCities.put("Podgorica", "42.44111,19.26361");
        hashMapLatLngOfCities.put("Amsterdam", "52.37403,4.88969");
        hashMapLatLngOfCities.put("Oslo", "59.91273,10.74609");
        hashMapLatLngOfCities.put("Warsaw", "52.22977,21.01178");
        hashMapLatLngOfCities.put("Lisabon", "38.71667,-9.13333");
        hashMapLatLngOfCities.put("Bucharest", "44.43225,26.10626");
        hashMapLatLngOfCities.put("Moscow", "55.75222,37.61556");
        hashMapLatLngOfCities.put("City of San Marino", "43.93667,12.44639");
        hashMapLatLngOfCities.put("Belgrade", "44.80401,20.46513");
        hashMapLatLngOfCities.put("Bratislava", "48.14816,17.10674");
        hashMapLatLngOfCities.put("Ljubljana", "46.05108,14.50513");
        hashMapLatLngOfCities.put("Madrid", "40.4165,-3.70256");
        hashMapLatLngOfCities.put("Stockholm", "59.33258,18.0649");
        hashMapLatLngOfCities.put("Bern", "46.94809,7.44744");
        hashMapLatLngOfCities.put("Ankara", "39.91987,32.85427");
        hashMapLatLngOfCities.put("Kiev", "50.45466,30.5238");
        hashMapLatLngOfCities.put("London", "51.50853,-0.12574");
        hashMapLatLngOfCities.put("Vatican City", "41.90236,12.45332");
        hashMapLatLngOfCities.put("Dublin", "53.350140,-6.266155");
    }
}
