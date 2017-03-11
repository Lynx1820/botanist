"""
All info comes from: http://www.houseplant411.com/houseplant?popup=2
"""

from bs4 import BeautifulSoup
import urllib.request, json, re

def parse_site(url):
    link = urllib.request.urlopen(url).read()
    soup = BeautifulSoup(link, "html.parser")

    titles = [s.get_text() for s in soup.find_all("div", attrs={"class":"post-meta-key"})]
    descriptions = soup.find_all("div", attrs={"class": "post-meta-value"})

    assert(len(titles) == len(descriptions))

    for d in descriptions:
        # some descriptions have unnecessary hover text (in 'span' tags), which we get rid of
        for pop_up in d.find_all("span"):
            pop_up.replace_with("")

    info = {}
    faq = {}

    for k, v in zip(titles, [d.text for d in descriptions]):
        if "?" in k: faq[k] = v
        else: info[k] = v

    info["FAQ"] = faq

    return info


def crawl_all():
    all_plant_info = {}
    plant_page = "http://www.houseplant411.com/houseplant?popup=2"
    r = urllib.request.urlopen(plant_page).read()
    soup = BeautifulSoup(r, "html.parser")
    links = soup.find_all("a")

    i = 0
    for link in links:
        plant_name = link.text
        page_url = link.get("href")
        page_url = re.sub("https", "http", page_url, count=1) # using 'https' gives a connection error for some reason
        all_plant_info[plant_name] = parse_site(page_url)
        i += 1
        print("done with ", i)

    with open("plant_info.json", "w") as outfile:
        json.dump(all_plant_info, outfile)


if __name__ == "__main__":
    crawl_all()
