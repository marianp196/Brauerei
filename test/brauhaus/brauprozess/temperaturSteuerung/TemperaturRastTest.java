/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brauhaus.brauprozess.temperaturSteuerung;

import brauhaus.bierData.brauelemente.TemperaturRastElement;
import brauhaus.brauprozess.temperaturSteuerung.util.HardwareMock;
import brauhaus.brauprozess.temperaturSteuerung.util.HardwareMockDTO;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sensoren.common.messergebnis.EEinheit;
import sensoren.common.messergebnis.MessergebnisMetrisch;

/**
 *
 * @author marian
 */
public class TemperaturRastTest {
    
    @Test
    public void Iterate_ShouldAufheizen_IfTempUnderMin() throws Exception
    {
        TemperaturRastElement rast = new TemperaturRastElement(10, 1000000000, 
                10 * 1000, 20 *1000);
        TemperaturRastSteuerer sut = createSut(rast);
        
        ArrayList<HardwareMockDTO> expected = new ArrayList<>();
        HardwareMockDTO dto;
        
        dto = new HardwareMockDTO(true, true, false, false, createM(5));
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, true, true, createM(6));
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, true, true, createM(7));        
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, true, true, createM(8));
        expected.add(dto);
        
        exec(expected, sut);
    }
    
     @Test
    public void Iterate_ShouldNotReActivateRuehrwerk_IfDeactivatedManually() throws Exception
    {
        TemperaturRastElement rast = new TemperaturRastElement(10, 1000000000, 
                10 * 1000, 20 *1000);
        TemperaturRastSteuerer sut = createSut(rast);
        
        ArrayList<HardwareMockDTO> expected = new ArrayList<>();
        HardwareMockDTO dto;
        
        dto = new HardwareMockDTO(true, true, false, false, createM(5));
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, false, true, false, createM(6));
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, false, true, false, createM(7));        
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, false, true, false, createM(8));
        expected.add(dto);
        
        exec(expected, sut);
    }
    
    @Test
    public void Iterate_ShouldStopAufheizen_IfMittelwertVonRastErreicht() throws Exception
    {
        TemperaturRastElement rast = new TemperaturRastElement(10, 1000000000, 
                10 * 1000, 20 *1000);
        TemperaturRastSteuerer sut = createSut(rast);
        
        ArrayList<HardwareMockDTO> expected = new ArrayList<>();
        HardwareMockDTO dto;
       
        dto = new HardwareMockDTO(true, true, false, false, createM(5));
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, true, true, createM(6));
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, true, true, createM(10));        
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, true, true, createM(12));
        expected.add(dto); 
        
        dto = new HardwareMockDTO(true, true, true, true, createM(14));
        expected.add(dto);         
        
        dto = new HardwareMockDTO(false, false, true, true, createM(16));
        expected.add(dto); 
        
        exec(expected, sut);
    }
    
    @Test
    public void Iterate_ShouldStartAufheizen_IfTempSinktUnterMin() throws Exception
    {
        TemperaturRastElement rast = new TemperaturRastElement(10, 1000000000, 
                10 * 1000, 20 *1000);
        TemperaturRastSteuerer sut = createSut(rast);
        
        ArrayList<HardwareMockDTO> expected = new ArrayList<>();
        HardwareMockDTO dto;
       
        dto = new HardwareMockDTO(false, false, false, false, createM(12));
        expected.add(dto);
        
        dto = new HardwareMockDTO(false, false, false, false, createM(11));
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, false, false, createM(9));        
        expected.add(dto);

        dto = new HardwareMockDTO(true, true, true, true, createM(8));        
        expected.add(dto);
        
        dto = new HardwareMockDTO(true, true, true, true, createM(11));        
        expected.add(dto);
        
        dto = new HardwareMockDTO(false, false, true, true, createM(16));        
        expected.add(dto);
        
        
        exec(expected, sut);
    }
    
    @Test
    public void Iterate_ShouldAbkuehlenLassen_IfTempOverMax() throws Exception
    {
        TemperaturRastElement rast = new TemperaturRastElement(10, 1000000000, 
                10 * 1000, 20 *1000);
        TemperaturRastSteuerer sut = createSut(rast);
        
        ArrayList<HardwareMockDTO> expected = new ArrayList<>();
        HardwareMockDTO dto;
       
        dto = new HardwareMockDTO(false, false, true, true, createM(40));
        expected.add(dto);
        
        dto = new HardwareMockDTO(false, false, false, false, createM(30));
        expected.add(dto);
        
        dto = new HardwareMockDTO(false, false, false, false, createM(25));        
        expected.add(dto);

        dto = new HardwareMockDTO(false, false, false, false, createM(14));        
        expected.add(dto);
        
        dto = new HardwareMockDTO(false, false, false, false, createM(11));        
        expected.add(dto);
        
        
        exec(expected, sut);
    }

    private void exec(ArrayList<HardwareMockDTO> expected, TemperaturRastSteuerer sut) throws Exception 
    {
        for(HardwareMockDTO dto : expected)
        {
           hardwaremMock.SetMockValue(dto);
           sut.Iterate(dto.getM());
        }
    }
    
    private MessergebnisMetrisch createM(double temp)
    {
        return new MessergebnisMetrisch(temp, EEinheit.Celsius);
    }
    
    private TemperaturRastSteuerer createSut(TemperaturRastElement rast)
    {
        return new TemperaturRastSteuerer(rast, hardwaremMock, hardwaremMock);
    }
    
    private HardwareMock hardwaremMock = new HardwareMock();

    
}
