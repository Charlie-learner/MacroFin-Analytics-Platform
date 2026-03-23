package com.example.hiveproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin(origins = "*") // 允许前端跨域请求
public class HiveQueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询1：三大股票指数的总交易量分布 (评估市场活跃度)
    @GetMapping("/stock-volume")
    public List<Map<String, Object>> getStockVolume() {
        String sql = "SELECT stock_index, " +
                "SUM(trading_volume) as total_volume " +
                "FROM finance_economics " +
                "GROUP BY stock_index";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询2：历年宏观经济趋势：平均 GDP 增长率 vs 失业率 (经典宏观经济学菲利普斯曲线衍生)
    @GetMapping("/economic-trends")
    public List<Map<String, Object>> getEconomicTrends() {
        String sql = "SELECT SUBSTR(trade_date, 1, 4) as year_val, " +
                "ROUND(AVG(gdp_growth), 2) as avg_gdp, " +
                "ROUND(AVG(unemployment_rate), 2) as avg_unemployment " +
                "FROM finance_economics " +
                "GROUP BY SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询3：消费者信心指数 (以10为区间分组) 对 消费支出金额的影响
    @GetMapping("/confidence-spending")
    public List<Map<String, Object>> getConfidenceSpending() {
        String sql = "SELECT FLOOR(consumer_confidence_index / 10) * 10 as confidence_bracket, " +
                "ROUND(AVG(consumer_spending_billion_usd), 2) as avg_spending " +
                "FROM finance_economics " +
                "WHERE consumer_confidence_index IS NOT NULL " +
                "GROUP BY FLOOR(consumer_confidence_index / 10) * 10 " +
                "ORDER BY confidence_bracket ASC";
        return jdbcTemplate.queryForList(sql);
    }


    // 查询4：大宗商品历史避险走势：原油价格 vs 黄金价格
    @GetMapping("/commodities-trend")
    public List<Map<String, Object>> getCommoditiesTrend() {
        String sql = "SELECT SUBSTR(trade_date, 1, 4) as year_val, " +
                "ROUND(AVG(crude_oil_price), 2) as avg_oil, " +
                "ROUND(AVG(gold_price), 2) as avg_gold " +
                "FROM finance_economics " +
                "GROUP BY SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询 5：利率对 GDP 增长的影响分析
    @GetMapping("/interest-gdp-correlation")
    public List<Map<String, Object>> getInterestGdpCorrelation() {
        String sql = "SELECT SUBSTR(trade_date, 1, 4) as year_val, " +
                "ROUND(AVG(interest_rate), 2) as avg_interest, " +
                "ROUND(AVG(gdp_growth), 2) as avg_gdp " +
                "FROM finance_economics " +
                "GROUP BY SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询 6：通货膨胀与失业率关系（菲利普斯曲线）
    @GetMapping("/phillips-curve")
    public List<Map<String, Object>> getPhillipsCurve() {
        String sql = "SELECT SUBSTR(trade_date, 1, 4) as year_val, " +
                "ROUND(AVG(inflation_rate), 2) as avg_inflation, " +
                "ROUND(AVG(unemployment_rate), 2) as avg_unemployment " +
                "FROM finance_economics " +
                "GROUP BY SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询 7：三大股指年度涨跌幅对比
    @GetMapping("/stock-performance")
    public List<Map<String, Object>> getStockPerformance() {
        String sql = "SELECT stock_index, " +
                "SUBSTR(trade_date, 1, 4) as year_val, " +
                "ROUND(AVG(open_price), 2) as avg_open, " +
                "ROUND(AVG(close_price), 2) as avg_close, " +
                "ROUND(((AVG(close_price) - AVG(open_price)) / AVG(open_price)) * 100, 2) as change_percent " +
                "FROM finance_economics " +
                "GROUP BY stock_index, SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC, stock_index";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询 8：企业并购与投资趋势
    @GetMapping("/ma-investment-trend")
    public List<Map<String, Object>> getMAInvestmentTrend() {
        String sql = "SELECT SUBSTR(trade_date, 1, 4) as year_val, " +
                "SUM(mergers_acquisitions_deals) as total_ma_deals, " +
                "ROUND(SUM(venture_capital_funding_billion_usd), 2) as total_vc_funding, " +
                "ROUND(AVG(bankruptcy_rate), 2) as avg_bankruptcy " +
                "FROM finance_economics " +
                "GROUP BY SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询 9：房地产市场与利率关系
    @GetMapping("/real-estate-interest")
    public List<Map<String, Object>> getRealEstateInterest() {
        String sql = "SELECT SUBSTR(trade_date, 1, 4) as year_val, " +
                "ROUND(AVG(real_estate_index), 2) as avg_real_estate, " +
                "ROUND(AVG(interest_rate), 2) as avg_interest " +
                "FROM finance_economics " +
                "GROUP BY SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // 查询 10：原油价格与通胀关系
    @GetMapping("/oil-inflation-correlation")
    public List<Map<String, Object>> getOilInflationCorrelation() {
        String sql = "SELECT SUBSTR(trade_date, 1, 4) as year_val, " +
                "ROUND(AVG(crude_oil_price), 2) as avg_oil, " +
                "ROUND(AVG(inflation_rate), 2) as avg_inflation " +
                "FROM finance_economics " +
                "GROUP BY SUBSTR(trade_date, 1, 4) " +
                "ORDER BY year_val ASC";
        return jdbcTemplate.queryForList(sql);
    }



}