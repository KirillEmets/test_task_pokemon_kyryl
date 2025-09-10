# 📋 Pokemon Viewer — README

## 🎯 Goal
- Android app in Kotlin using Jetpack Compose
- Display Pokémon list and details for selected Pokémon
- Focus: architecture, code quality, Favorites, image loading and in-memory caching without 3rd-party libraries

---

## 📱 Screen 1 — Pokémon List

**TopBar:** title + Favorites icon with counter  

**List items:** image, name, ID, Favorite icon, Delete icon  

**Logic:**
- Paging / lazy loading
- Add/remove from Favorites
- Delete items
- Navigate to details on click

---

## 📱 Screen 2 — Pokémon Details

**TopBar:** screen title  

**Content:** Pokémon name, image, ID, name, height, weight, Favorite icon  

**Logic:**
- Add/remove from Favorites
- Synchronized with list screen

---

## 🔄 General Logic
- Images loaded and cached in memory only (max 20 images; oldest removed when adding new)
- Custom in-memory cache handles image downloading and caching without Coil/Glide/Picasso
- Favorites state shared across both screens
- Functional UI (simplified is acceptable)

---

## ✅ Expectations
- Clear architecture (MVVM/MVI or other)
- Working custom in-memory image cache with downloading
- Correct UI behavior for Favorites and deletions

---

## ⭐ Bonus (optional)
- Unit tests
- Navigation Compose or other navigation approach
- Error handling / placeholder images

---

## ⏱ Estimated time
1.5–2 hours
