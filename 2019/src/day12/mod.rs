use crate::utils::lcm;
use regex::Regex;
use std::collections::HashSet;

use crate::utils;

#[derive(Debug, Clone, Copy)]
struct Planet {
    pos: (isize, isize, isize),
    v: (isize, isize, isize),
}

impl Planet {
    fn potential_energy(&self) -> isize {
        return self.pos.0.abs() + self.pos.1.abs() + self.pos.2.abs();
    }

    fn kinect_energy(&self) -> isize {
        return self.v.0.abs() + self.v.1.abs() + self.v.2.abs();
    }

    fn total_energy(&self) -> isize {
        return self.potential_energy() * self.kinect_energy();
    }
}

fn change_planets_by_gravity(mut p1: Planet, mut p2: Planet) -> (Planet, Planet) {
    if p2.pos.0 > p1.pos.0 {
        p2.v.0 -= 1;
        p1.v.0 += 1;
    } else if p2.pos.0 < p1.pos.0 {
        p2.v.0 += 1;
        p1.v.0 -= 1;
    }

    if p2.pos.1 > p1.pos.1 {
        p2.v.1 -= 1;
        p1.v.1 += 1;
    } else if p2.pos.1 < p1.pos.1 {
        p2.v.1 += 1;
        p1.v.1 -= 1;
    }

    if p2.pos.2 > p1.pos.2 {
        p2.v.2 -= 1;
        p1.v.2 += 1;
    } else if p2.pos.2 < p1.pos.2 {
        p2.v.2 += 1;
        p1.v.2 -= 1;
    }

    return (p1, p2);
}

fn apply_gravity(planets: &mut Vec<Planet>) {
    for i in 0..planets.len() {
        for i2 in i + 1..planets.len() {
            let p1 = planets.get(i).unwrap();
            let p2 = planets.get(i2).unwrap();
            let (new_p1, new_p2) = change_planets_by_gravity(*p1, *p2);
            planets[i] = new_p1;
            planets[i2] = new_p2;
        }
    }
}

fn update_positions(planets: &mut Vec<Planet>) {
    for planet in planets.iter_mut() {
        let v = planet.v;
        planet.pos.0 += v.0;
        planet.pos.1 += v.1;
        planet.pos.2 += v.2;
    }
}

fn get_system_energy(planets: Vec<Planet>) -> isize {
    return planets.iter().map(|p| p.total_energy()).sum();
}

fn parse_planets(input: Vec<String>) -> Vec<Planet> {
    let re = Regex::new(r"<x=(.*), y=(.*), z=(.*)>").unwrap();
    return input
        .iter()
        .map(|s| {
            let groups = re.captures_iter(s).next().unwrap();
            return Planet {
                pos: (
                    groups[1].parse().unwrap(),
                    groups[2].parse().unwrap(),
                    groups[3].parse().unwrap(),
                ),
                v: (0, 0, 0),
            };
        })
        .collect();
}

pub fn part1() -> isize {
    let input = utils::read_lines("resources/day12/day12.txt");
    let mut planets: Vec<Planet> = parse_planets(input);

    for _ in 0..1000 {
        apply_gravity(&mut planets);
        update_positions(&mut planets);
    }

    return get_system_energy(planets);
}

fn get_x_state(planets: &Vec<Planet>) -> Vec<(isize, isize)> {
    return planets.iter().map(|p| (p.pos.0, p.v.0)).collect();
}

fn get_y_state(planets: &Vec<Planet>) -> Vec<(isize, isize)> {
    return planets.iter().map(|p| (p.pos.1, p.v.1)).collect();
}

fn get_z_state(planets: &Vec<Planet>) -> Vec<(isize, isize)> {
    return planets.iter().map(|p| (p.pos.2, p.v.2)).collect();
}

pub fn part2() -> usize {
    let input = utils::read_lines("resources/day12/day12.txt");
    let mut planets: Vec<Planet> = parse_planets(input);

    let mut seen_x = HashSet::new();
    seen_x.insert(get_x_state(&planets));
    let mut seen_y = HashSet::new();
    seen_y.insert(get_y_state(&planets));
    let mut seen_z = HashSet::new();
    seen_z.insert(get_z_state(&planets));

    loop {
        apply_gravity(&mut planets);
        update_positions(&mut planets);
        let x_state = get_x_state(&planets);
        let y_state = get_y_state(&planets);
        let z_state = get_z_state(&planets);
        if seen_x.contains(&x_state) && seen_y.contains(&y_state) && seen_z.contains(&z_state) {
            break;
        }
        seen_x.insert(x_state);
        seen_y.insert(y_state);
        seen_z.insert(z_state);
    }

    let resp = lcm(seen_x.len(), lcm(seen_y.len(), seen_z.len()));
    return resp;
}
