use std::cmp::Ordering;
use std::collections::HashMap;

use crate::utils;

fn build(
    ingredient: &str,
    qty: usize,
    rules: &HashMap<String, (Vec<(usize, String)>, usize)>,
    bag: &mut HashMap<String, usize>,
) -> usize {
    let mut current_ingredient_stock = *bag.get(ingredient).unwrap_or(&0);
    if current_ingredient_stock >= qty {
        return 0;
    }

    let (recipe, qty_build_in_one_step) = rules.get(ingredient).unwrap();
    if recipe[0].1 == "ORE" {
        let mut ore_needed = 0;
        while current_ingredient_stock < qty {
            ore_needed += recipe[0].0;
            current_ingredient_stock += qty_build_in_one_step;
            bag.insert(ingredient.to_string(), current_ingredient_stock);
        }
        return ore_needed;
    }

    let mut total_ore_needed = 0;
    while current_ingredient_stock < qty {
        for (qty_needed, element) in recipe {
            let ore_for_element = build(element, *qty_needed, &rules, bag);
            total_ore_needed += ore_for_element;
            *bag.get_mut(element).unwrap() -= qty_needed;
        }
        current_ingredient_stock += qty_build_in_one_step;
        bag.insert(ingredient.to_string(), current_ingredient_stock);
    }

    return total_ore_needed;
}

fn get_ore_for(ingredient: &str, rules: HashMap<String, (Vec<(usize, String)>, usize)>) -> usize {
    return build(
        ingredient,
        rules.get(ingredient).unwrap().1,
        &rules,
        &mut HashMap::new(),
    );
}

fn parse_ingredient(s: String) -> (usize, String) {
    let mut split = s.split(" ");
    let quantity = split.next().unwrap().parse::<usize>().unwrap();

    return (quantity, split.next().unwrap().to_string());
}

fn parse_entry(s: String) -> (Vec<(usize, String)>, (usize, String)) {
    let mut first_split = s.split(" => ");
    let left_raw = first_split.next().unwrap().to_string();
    let right_raw = first_split.next().unwrap().to_string();

    let right_ingredient = parse_ingredient(right_raw);
    let left_ingredients = left_raw
        .split(", ")
        .map(|s| parse_ingredient(s.to_string()))
        .collect();
    return (left_ingredients, right_ingredient);
}

fn parse(v: Vec<String>) -> HashMap<String, (Vec<(usize, String)>, usize)> {
    return v
        .iter()
        .map(|s| parse_entry(s.to_string()))
        .map(|(inputs, (qty, element))| (element, (inputs, qty)))
        .collect();
}

pub fn part1() -> usize {
    let raw_input = utils::read_lines("resources/day14/day14.txt");
    let creation_rules = parse(raw_input);

    let resp = get_ore_for("FUEL", creation_rules);

    return resp;
}

fn build2(
    ingredient: &str,
    qty: usize,
    rules: &HashMap<String, (Vec<(usize, String)>, usize)>,
    bag: &mut HashMap<String, usize>,
) -> usize {
    if ingredient == "ORE" {
        return qty;
    }

    let mut current_ingredient_stock = *bag.get(ingredient).unwrap_or(&0);
    if current_ingredient_stock >= qty {
        return 0;
    }

    let (recipe, qty_build_in_one_step) = rules.get(ingredient).unwrap();

    let mut total_ore_needed = 0;
    let steps =
        (qty - current_ingredient_stock + qty_build_in_one_step - 1) / qty_build_in_one_step;
    for (qty_needed, element) in recipe {
        let ore_for_element = build2(element, *qty_needed * steps, &rules, bag);
        total_ore_needed += ore_for_element;
        if element != "ORE" {
            *bag.get_mut(element).unwrap() -= qty_needed * steps;
        }
    }
    current_ingredient_stock += qty_build_in_one_step * steps;
    bag.insert(ingredient.to_string(), current_ingredient_stock);

    return total_ore_needed;
}

fn get_ore_for2(
    ingredient: &str,
    qty: usize,
    rules: &HashMap<String, (Vec<(usize, String)>, usize)>,
) -> usize {
    return build2(ingredient, qty, &rules, &mut HashMap::new());
}

pub fn part2() -> usize {
    let raw_input = utils::read_lines("resources/day14/day14.txt");
    let creation_rules = parse(raw_input);
    let total_ore = 1_000_000_000_000;

    let mut min = 1;
    let mut max = 100_000_000;
    let resp = loop {
        let mid = (max + min) / 2;
        let ore = get_ore_for2("FUEL", mid, &creation_rules);
        match ore.cmp(&total_ore) {
            Ordering::Less => min = mid + 1,
            Ordering::Greater => max = mid - 1,
            Ordering::Equal => break mid,
        }
        if max < min {
            break max;
        }
    };

    return resp;
}
