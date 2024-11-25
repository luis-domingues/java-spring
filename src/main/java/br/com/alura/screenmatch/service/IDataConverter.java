package br.com.alura.screenmatch.service;

public interface IDataConverter {
   <T> T convertData(String json, Class<T> clazz);
}
