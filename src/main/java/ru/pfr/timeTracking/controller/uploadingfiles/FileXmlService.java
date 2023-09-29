package ru.pfr.timeTracking.controller.uploadingfiles;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import ru.pfr.timeTracking.model.timeTracking.dto.TimeTrackingDto;
import ru.pfr.timeTracking.model.timeTracking.entity.TimeParamSpecification;
import ru.pfr.timeTracking.service.timeTracking.TimeParamService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class FileXmlService {
    
    private final TimeParamService timeParamService;

    public FileXmlService(TimeParamService timeParamService) {
        this.timeParamService = timeParamService;
    }

    public HSSFWorkbook createHSSFWorkbook(Map<String, List<TimeTrackingDto>> map, LocalDate dateS, LocalDate datePo, String orgCode){
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Статистика");

        // счетчик для строк
        int rowNum = 0;

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Имя сотрудника");
        LocalDate tek = dateS;
        DateTimeFormatter formatterRu
                = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (int i = 1; tek.isBefore(datePo) || tek.isEqual(datePo); i++) {
            row.createCell(i).setCellValue(formatterRu.format(tek));
            tek = tek.plusDays(1);
        }

        // заполняем лист данными
        for (Map.Entry<String, List<TimeTrackingDto>> entry : map.entrySet()) {
            row = sheet.createRow(++rowNum);
            int i = 0;
            row.createCell(i++).setCellValue(entry.getKey());
            for (TimeTrackingDto timeTrackingDto : entry.getValue()) {
                String status = "";
                if (timeTrackingDto.getVacation()) {
                    status = "Отпуск";
                } else if (timeTrackingDto.getSickLeave()) {
                    status = "Больничный";
                } else if (timeTrackingDto.getBusinessTrip()) {
                    status = "Командировка";
                } else {
                    boolean b = true;
                    if (timeTrackingDto.isAbsenteeism()) {
                        if (LocalDateTime.now().isAfter(timeParamService.findOne(
                                TimeParamSpecification.codeEqual(orgCode)
                        ).get().getEvening(timeTrackingDto.getCurrentDate()))) {
                            b = false;
                            status = "Отсутствует";
                        }
                    }
                    if (b) {
                        formatterRu = DateTimeFormatter.ofPattern("HH:mm:ss");
                        status = timeTrackingDto.getBeginningOfWork() == null ? "" : formatterRu.format(timeTrackingDto.getBeginningOfWork());
                        status += " / ";
                        status += timeTrackingDto.getEndOfWork() == null ? "" : formatterRu.format(timeTrackingDto.getEndOfWork());
                    }

                }
                row.createCell(i++).setCellValue(status);
            }
        }
        return workbook;
    }

}
