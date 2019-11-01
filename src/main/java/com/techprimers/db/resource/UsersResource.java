package com.techprimers.db.resource;

import com.techprimers.db.model.Users;
import com.techprimers.db.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class UsersResource {
    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

    private int time = 1000;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/main")
    public String mainn(Model model) {
        model.addAttribute("name", "");
        return "main";
    }

    @GetMapping(value = "/all")
    public String all(Model model) {
        model.addAttribute("list", listToString(usersRepository.findAll()));
        return "all";
    }

    @PostMapping(value = "/main")
    public String mainPost(@RequestParam String name, @RequestParam String secondname, @RequestParam String email, Model model) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Users users = new Users();
        users.setName(name);
        users.setSecondname(secondname);
        users.setEmail(email);
        users.setStatus("");
        users.setTimeChange(null);
        users.setUriImage("defailt_image");

        usersRepository.save(users);

        model.addAttribute("name", users.getId());

        return "main";
    }

    @GetMapping("/getUser")
    public String getUser(Model model) {
        model.addAttribute("name", "");
        return "getUser";
    }

    @PostMapping(value = "/getUser")
    public String getUserPost(@RequestParam Integer id, Model model) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Users users = usersRepository.findOne(id);

        model.addAttribute("name", (users == null) ? "Пользовтель не найден." : users);

        return "getUser";
    }

    @GetMapping("/setUser")
    public String setUser(Model model) {
        model.addAttribute("name", "");
        return "setUser";
    }

    @PostMapping(value = "/setUser")
    public String setUser(@RequestParam Integer id, @RequestParam String status, Model model) {
        //TODO Переделать процесс обновления информации пользователя
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Users users = usersRepository.findOne(id);

        if (users == null) {
            model.addAttribute("name", "Пользовтель не найден.");
        } else {
            if ("Online".equals(status) || "Offline".equals(status)) {
                model.addAttribute("old_status", users.getStatus());
                usersRepository.delete(id);
                users.setStatus("Online".equals(status) ? "Online" : "Offline");
                users.setTimeChange(new Date());
                usersRepository.save(users);
            } else
                model.addAttribute("name", "Введено не верное состояние");

            model.addAttribute("id", users.getId());
            model.addAttribute("new_status", users.getStatus());
        }
        return "setUser";
    }

    @GetMapping("/loadImage")
    public String loadImaage(Model model) {
        return "loadImage";
    }

    @PostMapping("/loadImage")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename()+" ");
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("name", "URI:  " + uploadDirectory + "/" + fileNames.toString());

        return "loadImage";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        model.addAttribute("result", "");
        return "statistics";
    }

    @PostMapping(value = "/statistics")
    public String postStatistics(@RequestParam String status, @RequestParam String date, Model model) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (status.equals("") && date.equals("")) { // если фильтр пришёл пустой, то просто выводим всех пользователей
            model.addAttribute("result", listToString(usersRepository.findAll()));
        } else if (!status.equals("") && date.equals("")) { // если статус присутствует, а время нет, то просто выводим все пользовтелей с этим статусом
            model.addAttribute("result", listToString(usersRepository.findByStatus(status)));
        } else {
            DateFormat dateForm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
                List<Users> newList = new ArrayList<>();
                Date currentDate = dateForm.parse(date); // получили текущее время запроса
                //симулируем долгий запрос
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<Users> usersList = usersRepository.findByTimeChangeGreaterThan(currentDate);

                for (Users users : usersList) {
                    if (users.getStatus().equals(status))
                        newList.add(users);
                }
                model.addAttribute("result" , listToString(newList));


            } catch (ParseException e) {
                model.addAttribute("result" , "Введён неверный тип даты");
                return "statistics";
            }
        }

        return "statistics";
    }

    private String listToString(List<Users> list) {
        String res;
        if (list.size() > 0) {
            res = "";
            for (Users u : list) res = res + u  + " ";
        } else
            res = "Пусто";

        return  res;
    }

}
