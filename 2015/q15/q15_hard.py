def parse(entry_list):
    v = []
    for entry in entry_list:
        s = entry.split()
        ingredient = s[0][:-1]
        capacity = int(s[2][:-1])
        durability = int(s[4][:-1])
        flavor = int(s[6][:-1])
        texture = int(s[8][:-1])
        calories = int(s[-1])
        v.append({'capacity': capacity, 'durability': durability, 'flavor': flavor, 'texture': texture, 'calories': calories})
    return v

def get_mixture_score(weights, ingredients):
    capacity = sum([weights[i] * ingredients[i]['capacity'] for i in range(len(ingredients))])
    durability = sum([weights[i] * ingredients[i]['durability'] for i in range(len(ingredients))])
    flavor = sum([weights[i] * ingredients[i]['flavor'] for i in range(len(ingredients))])
    texture = sum([weights[i] * ingredients[i]['texture'] for i in range(len(ingredients))])
    calories = sum([weights[i] * ingredients[i]['calories'] for i in range(len(ingredients))])
    if capacity <=0 or durability <=0 or flavor <=0 or texture <= 0 or calories != 500:
        return 0

    return capacity * durability * flavor * texture

def process_entry(entry_list):
    ingredients = parse(entry_list)
    length = len(ingredients)
    teaspoons = 100
    maxi = 0
    for i in range(0, teaspoons+1):
        for j in range(0, teaspoons+1 - i):
            for k in range(0, teaspoons+1 -i -j):
                l = teaspoons - i -j -k
                maxi = max(maxi, get_mixture_score([i, j, k, l], ingredients))
    return maxi

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = process_entry(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
