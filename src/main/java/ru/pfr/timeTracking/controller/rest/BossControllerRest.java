package ru.pfr.timeTracking.controller.rest;

import lombok.RequiredArgsConstructor;
import org.opfr.springbootstarterauthsso.security.UserInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.pfr.timeTracking.aop.valid.ValidError;
import ru.pfr.timeTracking.controller.uploadingfiles.FileSystemStorageService;
import ru.pfr.timeTracking.controller.uploadingfiles.FileXmlService;
import ru.pfr.timeTracking.model.acs.entity.AccountSpecification;
import ru.pfr.timeTracking.model.timeTracking.dto.DateSPoDto;
import ru.pfr.timeTracking.model.timeTracking.dto.TimeTrackingDto;
import ru.pfr.timeTracking.model.timeTracking.dto.UUIDDto;
import ru.pfr.timeTracking.model.timeTracking.dto.WorkStatusDto;
import ru.pfr.timeTracking.service.acs.AccountsService;
import ru.pfr.timeTracking.service.timeTracking.TimeTrackingService;

import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/boss"})
//@Validated //включить проверку как параметров запроса, так и переменных пути в наших контроллерах
public class BossControllerRest {

    private final TimeTrackingService timeTrackingService;
    private final AccountsService accountsService;
    private final FileSystemStorageService fileSystemStorageService;
    private final FileXmlService xmlService;

    /**
     * Скачать документ по id
     */
    @ValidError
    @PostMapping("stat/getNameFile")
    public @ResponseBody ResponseEntity<?> getUri(
            @Valid @RequestBody DateSPoDto dateSPoDto,
            @AuthenticationPrincipal UserInfo user,
            Errors errors
    ) {
        try {
            Map<String, List<TimeTrackingDto>> map = timeTrackingService.getTable(
                    dateSPoDto.getDateS(),
                    dateSPoDto.getDatePo(),
                    user.getOrgCode()
            );


            String fileName = user.getUsername() + "_" + LocalDate.now() + ".xls";

            fileSystemStorageService.saveFile(
                    xmlService.createHSSFWorkbook(map, dateSPoDto.getDateS(), dateSPoDto.getDatePo(),
                            user.getOrgCode()), fileName);

            //Формируем ссылку для скачивания файла
            Path file = fileSystemStorageService.getPath(fileName);
            var uri = MvcUriComponentsBuilder.fromMethodName(
                            BossControllerRest.class,
                            "serveFile",
                            file.getFileName()
                                    .toString()
                    )
                    .build().toUri().toString();
            if (Files.exists(file)) {
                return ResponseEntity.ok()
                        .body(uri);
            }

            return ResponseEntity.badRequest().body("Ошибка при создании отчета!");

        } catch (
                Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }

    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileSystemStorageService.loadAsResource(filename);
        return ResponseEntity.ok().header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    /**
     * Фильтр по дате
     */
    @ValidError
    @PostMapping("/stat")
    public ResponseEntity<?> bossStatTable(
            @Valid @RequestBody DateSPoDto dateSPoDto,
            @AuthenticationPrincipal UserInfo user,
            Errors errors
    ) {
        try {
            return new ResponseEntity<>(
                    timeTrackingService.getTable(
                            dateSPoDto.getDateS(),
                            dateSPoDto.getDatePo(),
                            user.getOrgCode()
                    ),
                    HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /***
     * Установить командировку, больничный, отпуск, отсутствие
     */
    @ValidError
    @PostMapping("/edit/stat/{stat}/{login}")
    public ResponseEntity<?> editPeriodBoss(
            @Valid WorkStatusDto stat,
            @PathVariable String login,
            @Valid @RequestBody DateSPoDto dateSPoDto,
            Errors errors
    ) {
        try {
            var account = accountsService.findOne(
                    AccountSpecification.findByLogin(login)
            ).orElseThrow();

            timeTrackingService.save(stat, dateSPoDto, account);

            if (stat.getStat().equals("atwork")) {
                timeTrackingService.amnesty(dateSPoDto, login, account.getEmployees().getOrganizationEntity().getCODE());
            }

            return new ResponseEntity<>(
                    "",
                    HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PutMapping("/edit/{stat}/{id}")
    public String editStat(
            @Valid WorkStatusDto stat,
            @Valid UUIDDto id
    ) {
        return "viev/bossEdit/" + stat;
    }

}
