package com.example.hiveproject; // 注意：这里的包名必须和你自己的一致

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*") // 允许跨域请求，这对于前后端分离测试非常重要
public class HiveQueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 接口1：咖啡因摄入与睡眠/压力关系分析
    @GetMapping("/caffeine-sleep")
    public List<Map<String, Object>> getCaffeineAndSleep() {
        String sql = "SELECT caffeine_intake_cups, " +
                "ROUND(AVG(sleep_hours), 2) as avg_sleep_hours, " +
                "ROUND(AVG(stress_level), 2) as avg_stress_level " +
                "FROM smartphone_usage " +
                "GROUP BY caffeine_intake_cups " +
                "ORDER BY caffeine_intake_cups ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 接口2：不同职业的“内卷”与压力情况
    @GetMapping("/occupation-stress")
    public List<Map<String, Object>> getOccupationStress() {
        String sql = "SELECT occupation, " +
                "ROUND(AVG(weekend_screen_time_hours), 2) as avg_weekend_screen, " +
                "ROUND(AVG(daily_phone_hours), 2) as avg_daily_phone, " +
                "ROUND(AVG(stress_level), 2) as avg_stress " +
                "FROM smartphone_usage " +
                "GROUP BY occupation " +
                "ORDER BY avg_stress DESC";
        return jdbcTemplate.queryForList(sql);
    }

    // 接口3：社交媒体依赖与生产力分析
    @GetMapping("/social-productivity")
    public List<Map<String, Object>> getSocialProductivity() {
        String sql = "SELECT FLOOR(social_media_hours) as social_hours_bracket, " +
                "ROUND(AVG(work_productivity_score), 2) as avg_productivity " +
                "FROM smartphone_usage " +
                "GROUP BY FLOOR(social_media_hours) " +
                "ORDER BY social_hours_bracket ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 接口4：iOS vs Android 设备偏好与App使用量
    @GetMapping("/device-usage")
    public List<Map<String, Object>> getDeviceUsage() {
        String sql = "SELECT device_type, " +
                "COUNT(*) as user_count, " +
                "ROUND(AVG(app_usage_count), 0) as avg_apps_used " +
                "FROM smartphone_usage " +
                "GROUP BY device_type";
        return jdbcTemplate.queryForList(sql);
    }
}